(ns koans.koans
  (:require [re-frame.core :as re-frame :refer [subscribe dispatch]]
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
        ;; [:pre [:code [u/highlight {:language "clojure"} (str code)]]]
        [:pre [:code (pr-str code)]]
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
         [koan (str "Vectors are formed using square-brackets") '(= :_ (first [1 1 2 2 3 3 4 4 5 5])) assert]
         [koan (str "Maps are formed using curly-braces") '(= :_ (:b {:a 1 :b 2 :c 3})) assert]
         [:small [:em (str "Note that maps need to be balanced")]]
         [koan (str "Sets are formed by prefixing curly-braces with a hash") '(contains? #{1 2 3 4 5} :_) assert]]]))
