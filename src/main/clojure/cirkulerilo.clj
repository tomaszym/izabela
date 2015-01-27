(ns cirkulerilo
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all])
  (:import [org.tejo.stashek.cirkulerilo.facts ListFact BoardFact CardFact ChecklistFact]))

(defrule print-cirkulero-situation
  "baby-rule printing when there are conditions to cirlkurelumi"
  [ListFact (= name "Doing")
            (= id ?listId)]
;  [Order (> total 200)]
  =>
  (println ?listId)
  (println "zrobione")
)