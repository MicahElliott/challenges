(ns mde.tic-tac-toe)

;; ORIGINAL VERSION
(let ; EMEND: could use letfn for these two fns
  [check (fn [& sets] ; EMEND: naming as check-for-winner is more descriptive, and not actually sets
    (first (filter #(not (nil? %)) ; EMEND: remove is cleaner than filter here
      (map
        (fn [ss]
          ;; Check for sets of one suit to see if winner present
          (let [r (first (filter #(or (= % #{:x}) (= % #{:o})) ss))] ;; EMEND: getting a little dense to parse
            (if r (first r) nil))) ; EMEND: when is cleaner here
        sets))))]
  (defn ttt [board] ; EMEND: could spell out fully as tic-tac-toe for obviousness
    (check ; generate 4 colls for rows, cols, and diags
      (map set board) ; work on rows, checking for non-presence of X or O
      (map set (apply map list board)) ; transpose to work as cols
      (list (set (map #(nth (nth board %) %) (range 3)))) ; diag1, top-left to bottom-right
      (list (set (map #(nth (nth board %) (- 2 %)) (range 3)))) ; other diagonal
)))

;; These are grids that need eyeballing, so better to align as such.
;; Would be nice to have these in the test file.
(assert (= :x (ttt [[:x :o :x]
                    [:x :o :o]
                    [:x :x :o]])))
(assert (= :o (ttt [[:o :x :x] [:x :o :x] [:x :o :o]])))
(assert (nil? (ttt [[:x :o :x] [:x :o :x] [:o :x :o]])))

;; 1. WHAT IS THE CODE TRYING TO ACCOMPLISH?

;; This is Tic-Tac-Toe! If a "line" of three-in-a-row is found on the
;; board, that "suit" is the winner.

;; 2. DESCRIBE AT A HIGH LEVEL HOW IT WORKS.

;; The main game function `ttt` creates all four groupings of possible
;; winning combinations for tic-tac-toe. The groupings are rows,
;; columns, and two diagonals. By checking each for a "dominant suit"
;; in each (a `set` with only one suit present), the `check` function
;; can determine a winner.

;; 3. WHAT FEEDBACK WOULD YOU PROVIDE IN A CODE REVIEW?

;; See inline comments.
;; Also, limitations should be documented.

;; 4. (BONUS) HOW WOULD YOU WRITE IT?

(letfn [(find-single-set-winner [ss]
          (when-let [r (first
                        (filter #(or (= % #{:x})
                                     (= % #{:o}))
                                ss))]
            (first r)))
        (check [& lines]
          (first
           (remove nil?
                   (map find-single-set-winner lines))))
        (ttt [board]
          (check
           (map set board)
           (map set (apply map list board))
           (list (set (map #(nth (nth board %) %)
                           (range 3))))
           (list (set (map #(nth (nth board %) (- 2 %))
                           (range 3))))))]

  (assert (= :x (ttt [[:x :o :x]
                      [:x :o :o]
                      [:x :x :o]])))

  (assert (= :o (ttt [[:o :x :x]
                      [:x :o :x]
                      [:x :o :o]])))

  (assert (nil? (ttt [[:x :o :x]
                      [:x :o :x]
                      [:o :x :o]]))))
