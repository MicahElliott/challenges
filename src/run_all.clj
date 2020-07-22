(ns run-all
  (:require [mde.word-search :as word-search]
            [mde.unlock :as unlock])
  (:gen-class))

(defn -main [& args]
  (let [word "HELLO"]
    (println "WORD SEARCH")
    (println "===========")
    (println "The word" word "appears in this matrix"
             (word-search/count-word-in-matrix nil word)
             "times.")
    (println word-search/board))
  (let [board (range 1 10)
        moves [1 2 3]]
    (println "\nUNLOCKING")
    (println "=========")
    (println "The unlock pattern" moves "is"
             (if (unlock/unlock (range 1 10) moves)
               "legal"
               "illegal")
             "for the standard 3x3 grid.")))
