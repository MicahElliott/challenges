(ns mde.unlock
  "Support unlocking a device having some square shaped grid via a lock
  pattern sequence (moves).

  \"Rules\" are implemented as functions herein.

  Assumptions:
  - boards and moves are sequences of integers

  TODO:
  - logging each failure
  - generalize board size")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Board Config

(def min-move-count
  "Support a minimal number of moves."
  3)

(def board-size
  "Hard-code board to one size."
  9)

(defn legal-board-size?
  "**Rule:** only square `board`s.
  _Return boolean._"
  [board]
  ;; (contains? #{4 9 16 25 36} (count board))
  ;; General checking for squareness for any size grid.
  (let [c (count board)]
    (zero? (int (rem c (Math/sqrt c))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Moves

(defn legal-move-9?
  "Look at a single move as a `beg`inning and `end`, and look for all
  known illegals.
  _Return boolean._
  NOTE: this is a simplistic, specific, hard-code approach for a 9-square
        grid, rather than generalized for larger boards."
  [beg end]
  (not (case beg
         1 (contains? #{3 7 9} end)
         2 (contains? #{8} end)
         3 (contains? #{1 7 9} end)
         4 (contains? #{6} end)
         5 (contains? #{} end)
         6 (contains? #{4} end)
         7 (contains? #{1 3 9} end)
         8 (contains? #{2} end)
         9 (contains? #{1 3 7} end))))

(defn moves-within-board?
  "**Rule:** all `moves` are within size of board. _Return boolean._"
  [moves]
  (every? #(<= 1 % board-size) moves))

(defn moves-non-overlapping?
  "**Rule:** `moves` are not repeated.
  _Return boolean._
  NOTE: this is too simple to warrant a fn, but its name better
        serves intent."
  [moves]
  (when (seq moves) ; protect from empty moves
    (apply distinct? moves)))

(defn moves-sufficiently-many?
  "**Rule:** `moves` must be at least `min-move-count`."
  [moves]
  (<= min-move-count (count moves)))

(defn moves-not-blacklisted?
  "**Rule:** break `moves` down into one-at-a-time steps, checking each
  for legality.
  _Return boolean_."
  [moves]
  (every? true? ; every move must be legal
          (map #(apply legal-move-9? %)
               ;; Break moves down into one-at-a-time. Using
               ;; `partition` instead of `reduce` to simplify
               ;; returning original moves sequence.
               (partition 2 1 moves))))

(def movement-rules
  "Comprehensive list of all rules to be applied."
  [moves-within-board?
   moves-non-overlapping?
   moves-sufficiently-many?
   moves-not-blacklisted?])

(defn all-moves-legal?
  "Apply movement rules collectively to `moves` wherein all must pass.
  _Return boolean_."
  [moves]
  ((apply every-pred ; provide short-circuiting; cleaner than `and`
          movement-rules)
   moves))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Public Entry Point

(defn unlock
  "Determine if sequence of `moves` is a valid unlock pattern
  for given `board`.
  _Return the original `moves` pattern if valid/legal, or `nil`otherwise._"
  [board moves]
  (when (and (legal-board-size? board) (all-moves-legal? moves))
    moves))
