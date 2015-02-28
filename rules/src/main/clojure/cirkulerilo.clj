(ns cirkulerilo
  (:import [org.tejo.iza.rules.facts ChecklistFact ListFact CardFact BoardFact CheckItemFact]
           [org.tejo.iza.rules.facts.control DissenduAlvokon Memorigu Kunmetu]
           [scala.collection.immutable Vector Vector$])
  (:refer-clojure :exclude [==])
  (:require [clara.rules.accumulators :as acc]
            [clara.rules :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as format]))
;            ))
(def trello-date (format/formatters :date-time))
(defn week-before-now? [date]
  (t/before? (format/parse trello-date date) (t/plus (t/now) (t/weeks 1))))
(defn day-before-now? [date]
  (t/before? (format/parse trello-date date) (t/plus (t/now) (t/days 1))))

(defrule alvoku-rule
  "When there's a list named 'Aktuala' with a card 'Stirkarto' on it with some due date and the first position on a checklist IS checked and the second IS NOT"
  [ListFact (= name "Aktuala")
   (= ?listId id)]
  [CardFact (= name "Stirkarto")
   (true? hasDue) (= ?due due)
   (= listId ?listId)
   (= ?agrodojCardId id)]
  [ChecklistFact (= cardId ?agordojCardId)
   (= ?checklistId id)]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 0) (true? complete )]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 1) (false? complete)]

  =>
  (insert! (DissenduAlvokon. ?due))
  )

(defquery alvoku-query
  "Query to find alvoko situation"
  []
  [?alvoku <- DissenduAlvokon])

(defrule memorigu-rule
  "Same as alvoko-rule but:
   - the second item is complete,
   - it's less than a week to due date.
  "
  [ListFact (= name "Aktuala")
   (= ?listId id)]
  [CardFact (= name "Stirkarto")
   (true? hasDue) (= ?due due)
   (= listId ?listId)
   (= ?agrodojCardId id)]
  [ChecklistFact (= cardId ?agordojCardId)
   (= ?checklistId id)]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 0) (true? complete )]
  [CheckItemFact (= checklistId ?checklistId)
   (= idx 1) (true? complete)]
  [:or
   ; unua Memorigo: la tria taskero nekompletita, la limdato en malpli ol unu semajno
   [:and [CheckItemFact (= checklistId ?checklistId) (= idx 2) (false? complete)] [:test (week-before-now? ?due)]]
   ; dua Memorigo: la tria taskero kompletita (te. unua Memorigu), la limdato en malpli ol unu tago
   [:and [CheckItemFact (= checklistId ?checklistId) (= idx 2) (true? complete)] [:test (day-before-now? ?due)]]]
  [CheckItemFact (= checklistId ?checklistId) (= idx 3) (false? complete)] ; la kvara taskero nekompletita (te. la dua Memorigu)
  =>
  (insert! (Memorigu. ?due))
  )

(defquery memorigu-query
  "Query to find Memorigu situation"
  []
  [?memorigu <- Memorigu])

(defrule kunmetu-rule
  " <priskribo> "
  [ListFact (= name "Aktuala")
   (= ?listId id)]
  [CardFact (= name "Stirkarto")
            (= listId ?listId)
            (= ?agrodojCardId id)]
  [ChecklistFact (= cardId ?agordojCardId)
                 (= ?checklistId id)]
  [CheckItemFact (= checklistId ?checklistId) (= idx 0) (true? complete)]
  [CheckItemFact (= checklistId ?checklistId) (= idx 1) (true? complete)]
  [CheckItemFact (= checklistId ?checklistId) (= idx 2) (true? complete)]
  [CheckItemFact (= checklistId ?checklistId) (= idx 3) (true? complete)]
  [CheckItemFact (= checklistId ?checklistId) (= idx 4) (true? complete)] ; konfirmo: kunmetu
  [CheckItemFact (= checklistId ?checklistId) (= idx 5) (false? complete)] ; ankoraŭ nekunmetita
  =>
  (insert! (Kunmetu. ?listId))
  )

(defquery kunmetu-query
  "Query to find Kunmetu situation"
  []
  [?kunmetu <- Kunmetu])
;(defn colon-colon [items value] (.$colon$plus$ items value))
;(defn all-scala-list
;  ""
;  ([]
;    (accumulate
;      :initial-value (. Vector$ empty())
;      :reduce-fn (fn [items value] (colon-colon items value))
;      :combine-fn concat))
;  )


(defquery kontribuintoj-query
  "Query to find kontribuintoj "
  []
  [ListFact (= name "Aktuala")
            (= ?listId id)]
  [?kontribuintoj <- (acc/all) from [CardFact (= listId ?listId) (not= name "Stirkarto")]])
