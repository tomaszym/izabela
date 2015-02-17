(ns cirkulerilo
  (:import [org.tejo.iza.rules.facts ChecklistFact ListFact CardFact BoardFact]
           [org.tejo.iza.rules.facts.control DissenduAlvokon])
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all]))

(defrule alvoko-rule
  "When there are conditions sends out the emails"
  [ListFact (= name "Aktuala")
            (= ?listId id)]

  [CardFact (= name "Stirkarto")
            (= ?agrodojCardId id)]

  [ChecklistFact (= idCard ?agordojCardId)
;                 (= "Dissendi alvokon @tomaszszymula" (.name (.head checkItems)))
                 (= "complete" (.state (.head checkItems)))
;                 (= "Alvoko dissendita @stashek" (.name (.head (.tail checkItems))))]
                 (= "incomplete" (.state (.head (.tail checkItems))))]
  =>
  (insert! (DissenduAlvokon.))
)

(defquery alvoko-query
  "Query to find alvoko situation"
  []
  [?alvoko <- DissenduAlvokon])

;                 (not (.isCompleted ?secondCheckItem))
;                 (.isCompleted ?firstCheckItem)