(ns koans.koans
  (:require [re-frame.core :as re-frame :refer [subscribe dispatch]]
            [reagent.core :as reagent]
            [cljs.js :as cljs]
            [koans.utils :as u]))

(defonce compiler-state
  (cljs/empty-state))

(defn- eval [input]
  (cljs/eval-str compiler-state (str input) nil
                 {:eval cljs/js-eval} #(:value %)))

(defn- koan [intro code assert]
  (fn [intro code]
    (let [v (if-not (re-matches #"_" (str code)) (eval code) false)]
      (assert intro v)
      [:div.koan
       [:p [:small intro]]
       [:div.code {:class (if v "success" "")}
        [u/highlight {:language "clojure"} (str code)]
        ;; [:pre [:code (pr-str code)]]
        [:pre (str "=> " v)]]])))

(defn assertions []
  (let [completed? @(subscribe [:assertions-completed?])
        assert     #(dispatch [:assertion %1 %2])]
    [:div.card
     [:h3 "Assertions"]
     [:i.ion-checkmark-round.checkmark {:class (if completed? "succeeded" "failed")}]
     [:p (str "Clojure is a dialect of lisp and in lisp we mostly work with lists."
              " A list is defined using parenthesis and if the first element of the"
              " list is a symbol, the list will be evaluated as a function."
              " In the assertions below you can see this in action. In the first assertion"
              " `=` is the function-name and the following elements of the list is"
              " it's arguments")]
     [koan (str "Only real truths") '(= :_ true) assert]
     [koan (str "Same for falsehoods") '(= :_ false) assert]
     [koan (str "More than the first") '(> :_ 1) assert]
     [koan (str "You can compare two of the same type") '(= :_ (keyword "bar")) assert]]))

(defn data-structures []
  (let [completed? @(subscribe [:data-structures-completed?])
        assert     #(dispatch [:data-structure %1 %2])]
    [:div.card
        [:h3 "Data-structures"]
        [:i.ion-checkmark-round.checkmark {:class (if completed? "succeeded" "failed")}]
        [:p (str "As mentioned, lists are very common in Clojure. But there are many "
                 "other valuable data-structures you can play with.")]
        [:div
         [koan (str "Vectors are formed using square-brackets. [:a :b :c :d]") '(= :_ (first [1 1 2 2 3 3 4 4 5 5])) assert]
         [koan (str "Maps are formed using curly-braces. {:hello \"world\" :foo \"bar\"}") '(= :_ (:b {:a 1 :b 2 :c 3})) assert]
         [:small [:em (str "Note that maps need to be balanced")]]
         [koan (str "Sets are formed by prefixing curly-braces with a hash. #{:a 1 :b :c 3}") '(contains? #{1 2 3 4 5} :_) assert]
         [:small [:em (str "Sets always contain unique values")]]]
     [:div
      [:p (str "These are all collections and collections share a bit of API that's"
               " used for testing/verification and some modification."
               " Although most manipulation is specific to each data-type.")]
      [:p (str "Sequential data-structures are called Seq in Clojure, these share even more API.")]]]))

(defn lazy-sequences []
  (let [completed? @(subscribe [:lazy-sequences-completed?])
        assert     #(dispatch [:lazy-sequence %1 %2])]
    [:div.card
     [:h3 "Lazy Sequences"]
     [:i.ion-checkmark-round.checkmark {:class (if completed? "succeeded" "failed")}]
     [:p (str "Languages are often separated into categories of lazy or eager evaluation. "
              "As you might have guessed, Clojure falls into the latter. "
              "What that means, is we can have data-structures that contain an endless amount "
              "of items, but only the ones we observe are taken into account.")]
     [:div
      [koan (str "We can make small sequences") '(= :_ (take 2 (range 3))) assert]
      [koan (str "Or huge at the same cost") '(= :_ (take 2 (range 999999999999))) assert]]
     [:div
      [koan (str "They can also be a product of our own structures") '(= :_ (nth (cycle [:a :b :c]) 3)) assert]]]))

(defn threading-macros []
  (let [completed? @(subscribe [:threading-macros-completed?])
        assert     #(dispatch [:threading-macro %1 %2])]
    [:div.card
     [:h3 "Threading Macros"]
     [:i.ion-checkmark-round.checkmark {:class (if completed? "succeeded" "failed")}]
     [:p (str "Threading is an eloquent solution to making code more readable."
              "It's API is also fairly similar to that of transducers, so we can "
              "in many cases also gain heavily in performance. Yay!")]
     [:div
      [koan (str "Thread first -> will direct the output of fn to the first argument of the next fn")
       '(= :_ (-> [:a :b :c :d] (nth 1))) assert]
      [koan (str "Thread last ->> will direct the output of fn to the last argument of the next fn")
       '(= :_ (->> [:a :b :c :d] (mapv name))) assert]
      ]
     [:div
      [:p (str "These two are the most commonly used threading-macros. Clojure also "
               "has a few special ones for other common use-cases. These include"
               "some-> some->> as-> cond-> cond->>")]
      [:p (str "I'm not going to cover transducers in-depth, but feel free to ask ;)")]]]))
