(ns mde.word-search
  (:require [clojure.string :as str]))

(def board
  "Example game board representation; closest thing to what you might
  see in a text file, but including as code for toy implementation
  convenience. Needs splitting, trimming, etc to be a workable matrix."
  "AOTDLROW
LCBMUMLU
DRUJDBLJ
PAZHZZEF
BCZELFHW
RKULVPPG
ALBLPOPQ
BEMOPPJY")

(def rows
  "Rows of the board; a sequence of strings"
  (map str/trim
       (str/split board #"\n")))

(def cols
  "Columns of the board; transpose of matrix"
  (apply mapv str rows))

(defn reverse-mat
  "Reverse every string."
  [strs]
  (map (comp (partial apply str) reverse)
       strs))

;; Cutesy naming, reversing "rows" as "wors"
(def wors (reverse-mat rows))
(def locs (reverse-mat cols))

(comment ; experimental steps

  ;; simple to check for word presence
  (str/index-of "AOTDLfooROW" "foo") ;=> 5

  ;; convert location indexes to 1s
  (map #(if (str/index-of % "HELLO") 1 0)
       cols) ;=> (0 0 0 1 0 0 0 0)

  ;; ok, so let's one-hot encode it all and sum
  (apply +
         (map #(apply + %)
              (for [line [rows cols wors locs]]
                (mapv #(if (str/index-of % "HELLO") 1 0)
                      line)))) ;=> 2, Yay
  )

(defn sum [nums] (apply + nums)) ; convenience to avoid apply

(defn count-word-in-matrix
  "Create a summable one-hot encoding to represent presence of a `word.`"
  [_matrix word]
  (let [hot1 (for [line [rows cols wors locs]]
               (mapv #(if (str/index-of % word) 1 0)
                     line))]
    (sum (map sum hot1))))
