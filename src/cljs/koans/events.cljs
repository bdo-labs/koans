(ns koans.events
  (:require [re-frame.core :as re-frame :refer [trim-v reg-event-db reg-event-fx]]
            [koans.state :as state]))

(def interceptors
  [trim-v])

(reg-event-db
 :initialize-db
 (fn [_ _]
   state/default-state))

(reg-event-db
 :set-active-panel
 [interceptors]
 (fn [db [active-panel]]
   (assoc db :active-panel active-panel)))

(reg-event-db
 :assertion
 [interceptors]
 (fn [db [k v]]
   (assoc-in db [:assertions k] v)))

(reg-event-db
 :data-structure
 [interceptors]
 (fn [db [k v]]
   (assoc-in db [:data-structures k] v)))

(reg-event-db
 :threading-macro
 [interceptors]
 (fn [db [k v]]
   (assoc-in db [:threading-macros k] v)))

(reg-event-db
 :lazy-sequence
 [interceptors]
 (fn [db [k v]]
   (assoc-in db [:lazy-sequence k] v)))
