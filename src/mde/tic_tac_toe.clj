(ns mde.challenges
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(let
  [check (fn [& sets]
    (first (filter #(not (nil? %))
      (map
        (fn [ss]
          (let [r (first (filter #(or (= % #{:x}) (= % #{:o})) ss))]
            (if r (first r) nil)))
        sets))))]
  (defn ttt [board]
    (check
      (map set board)
      (map set (apply map list board))
      (list (set (map #(nth (nth board %) %) (range 3))))
      (list (set (map #(nth (nth board %) (- 2 %)) (range 3))))
)))

(assert (= :x (ttt [[:x :o :x] [:x :o :o] [:x :x :o]])))
(assert (= :o (ttt [[:o :x :x] [:x :o :x] [:x :o :o]])))
(assert (nil? (ttt [[:x :o :x] [:x :o :x] [:o :x :o]])))

;; 1. What is the code trying to accomplish?

;; This is Tic-Tac-Toe!
;; See new inline comments.

;; 2. Describe at a high level how it works.

;; 3. What feedback would you provide in a code review?

;; I would gently state that it should have a bit of reformatting to
;; conform to our agreed upon conventions.

;; 4. (Bonus) How would you write it?


(defn check [& sets]
  (first
   (remove nil?
           (map
            (fn [ss]
              (let [r (first
                       (filter #(or (= % #{:x}) (= % #{:o}))
                               ss))]
                (when r (first r))))
            sets))))

(defn ttt [board]
  (check
   (map set board)
   (map set (apply map list board))
   (list (set (map #(nth (nth board %) %) (range 3))))
   (list (set (map #(nth (nth board %) (- 2 %)) (range 3))))))

(ttt [[:x :o :x]
      [:x :o :o]
      [:x :x :o]])

(letfn [(foo [ss]
          (when-let [r (first
                        (filter #(or (= % #{:x})
                                     (= % #{:o}))
                                ss))]
            (first r)))
        (check [& sets]
          (first
           (remove
            nil?
            (map
             foo
             sets))))
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
