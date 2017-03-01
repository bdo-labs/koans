(ns koans.state
  (:require [clojure.test.check.generators :as gen]
            [clojure.spec :as s]))

(def default-state
  {:assertions      {}
   :data-structures {}})
