(ns cirkulerilo
  (:import [org.tejo.iza.rules.facts ChecklistFact ListFact CardFact BoardFact CheckItemFact]
           [org.tejo.iza.rules.facts.control DissenduAlvokon Memorigo])
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as format]))
;            ))
(def trello-date (format/formatters :date-time))
(defrule alvoko-rule
  "When there's a list named 'Aktuala' with a card 'Stirkarto' on it with some due date and the first position on a checklist IS checked and the second IS NOT"
  [ListFact (= name "Aktuala")
   (= ?listId id)]
  [CardFact (= name "Stirkarto")
   (true? hasDue)
   (= ?due due)
   (= ?agrodojCardId id)]
  [ChecklistFact (= cardId ?agordojCardId)
   (= ?checklistId id)]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 0)
   (true? complete )]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 1)
   (false? complete)]

  =>
  (insert! (DissenduAlvokon. ?due))
  )

(defquery alvoko-query
  "Query to find alvoko situation"
  []
  [?alvoko <- DissenduAlvokon])

(defrule memorigo-rule
  "Same as alvoko-rule but:
   - the second item is complete,
   - it's less than a week to due date.
  "
  [ListFact (= name "Aktuala")
   (= ?listId id)]
  [CardFact (= name "Stirkarto")
   (true? hasDue)
   (= ?due due)
   (= ?agrodojCardId id)]
  [:test (t/before? (format/parse trello-date ?due) (t/plus (t/now) (t/months 1))) ]
  [ChecklistFact (= cardId ?agordojCardId)
   (= ?checklistId id)]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 0)
   (true? complete )]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 1)
   (true? complete)]

  =>
  (insert! (Memorigo.))
  )

(defquery memorigo-query
  "Query to find alvoko situation"
  []
  [?memorigo <- Memorigo])
