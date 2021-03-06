(ns koans.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub]]
            [clojure.spec :as s]
            [koans.utils :as u]))

(reg-sub
 :active-panel
 (fn [db]
   (:active-panel db)))


;; Assertions

(reg-sub
 :assertions
 (fn [db]
   (vals (:assertions db))))

(reg-sub
 :assertions-completed?
 :<- [:assertions]
 (fn [assertions]
   (empty? (remove true? assertions))))


;; Data-structures

(reg-sub
 :data-structures
 (fn [db]
   (vals (:data-structures db))))

(reg-sub
 :data-structures-completed?
 :<- [:data-structures]
 (fn [data-structures]
   (empty? (remove true? data-structures))))


;; Threading-macros

(reg-sub
 :threading-macros
 (fn [db]
   (vals (:threading-macros db))))

(reg-sub
 :threading-macros-completed?
 :<- [:threading-macros]
 (fn [threading-macros]
   (empty? (remove true? threading-macros))))


;; lazy-sequences

(reg-sub
 :lazy-sequences
 (fn [db]
   (vals (:lazy-sequences db))))

(reg-sub
 :lazy-sequences-completed?
 :<- [:lazy-sequences]
 (fn [lazy-sequences]
   (empty? (remove true? lazy-sequences))))


;; Stats

(reg-sub
 :koans
 :<- [:assertions]
 :<- [:data-structures]
 (fn [& koans]
   (->> koans
        (flatten)
        (remove #(not (boolean? %))))))

(reg-sub
 :completed-koans
 :<- [:koans]
 (fn [koans]
   (remove false? koans)))

(reg-sub
 :percent-completed
 :<- [:koans]
 :<- [:completed-koans]
 (fn [[total-koans completed-koans]]
   (int (* (/ (count completed-koans) (count total-koans)) 100))))
