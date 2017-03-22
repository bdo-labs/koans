(ns koans.utils
  (:require [reagent.core :as reagent]
            [cljsjs.react-highlight]))

(defn log [& args]
  (do (apply js/console.log args)
      args))

(def highlight
  (reagent/adapt-react-class js/Highlight))
