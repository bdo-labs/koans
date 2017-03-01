(ns koans.views
  (:require [re-frame.core :as re-frame :refer [subscribe dispatch]]
            [koans.koans :as koans]
            [koans.utils :as u]))

(defn- footer []
  [:footer.flex.row.align-center.justify-center
   (str "It's on github   ")
   [:a {:href "//github.com/bdo-labs/koans"} [:i.ion-social-github]]
   (str "   go grab it :)")])

(defn- koans-panel []
  (let [percent-completed (subscribe [:percent-completed])]
    (fn []
      [:div.container
       [:div.completed {:style {:width (str @percent-completed "%")}}]
       [:header.flex.column
        [:div.text-width
         [:h1 "Clojure " [:strong "Koans"]]
         [:p (str "A koan is a riddle or puzzle that Zen Buddhists use during "
                  "meditation to help them unravel greater truths about the world"
                  " and about themselves. Zen masters have been testing their"
                  " students with these stories, questions, or phrases for centuries.")]
         [:p (str "For quick-reference to how these Koans are solved, I recommend having a look at ")
          [:a {:href "//cljs.info"} "cljs.info"]]
         [:em (str "Ohh! And just replace `:_` with whatever value you think is correct")]]]
       [koans/assertions]
       [koans/data-structures]
       [footer]])))

;; main

(defn- panels [panel-name]
  (case panel-name
    :koans-panel [koans-panel]
    [:div]))

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      [panels @active-panel])))
