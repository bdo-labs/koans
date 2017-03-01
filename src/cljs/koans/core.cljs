(ns koans.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [koans.events]
            [koans.subs]
            [koans.routes :as routes]
            [koans.views :as views]
            [re-frisk.core :refer [enable-re-frisk!]]
            [nightlight.repl-server]
            ;; [cljsjs.react-highlight]
            ))

(def debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
