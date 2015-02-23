(ns cirkulerilo
  (:import [org.tejo.iza.rules.facts ChecklistFact ListFact CardFact BoardFact CheckItemFact]
           [org.tejo.iza.rules.facts.control DissenduAlvokon]
           [scala Some])
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all]))
;            [clj-time.core :as time]
;            [clj-time.format :as format]))

(defrule alvoko-rule
  "When there's a list named 'Aktuala' with a card 'Stirkarto' on it and the first position on a checklist IS checked and the second IS NOT"
  [ListFact (= name "Aktuala")
            (= ?listId id)]

  [CardFact (= name "Stirkarto")
            (= ?agrodojCardId id)]

  [ChecklistFact (= idCard ?agordojCardId)
                 (= ?checklistId id)]
  [CheckItemFact (= idList ?checklistId)
                 (= idx 0)
                 (true? complete )]
  [CheckItemFact (= idList ?checklistId)
                 (= idx 1)
                 (false? complete )]

  =>
  (insert! (DissenduAlvokon.))
  )

(defquery alvoko-query
  "Query to find alvoko situation"
  []
  [?alvoko <- DissenduAlvokon])
;
;(defrule unua-memorigo-rule
;  "When there's a list named 'Aktuala' with a card 'Stirkarto' on it with some due date
;   and the first two positions ARE complete and the third is NOT"
;  [ListFact (= name "Aktuala")
;            (= ?listId id)]
;
;  [CardFact (= name "Stirkarto")
;            (= ?agrodojCardId id)]
;
;  [?checklist <- ChecklistFact (= idCard ?agordojCardId)]
;  [ :test (.checkedAt ?checklist 0)]
;  [ :test (.checkedAt ?checklist 1)]
;  [ :test (.uncheckedAt ?checklist 2)]
;  =>
;  (insert! (DissenduAlvokon.))
;  )
;
;(defquery unua-memorigo-query
;  "Query to find unua-memorigo situation"
;  []
;  [?alvoko <- DissenduAlvokon])
