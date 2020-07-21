(ns mde.unlock-test
  (:require [mde.unlock :as sut]
            [clojure.test :as t :refer [is deftest testing]]))

(def board9 [1 2 3
             4 5 6
             7 8 9])

(def unlock (partial sut/unlock board9))

(deftest unlocking
  (testing "legal sequences"
    (is (unlock [1 2 3]) "simplest")
    (is (unlock (range 1 10)) "long")
    (is (unlock [2 1 6]) "knight"))
  (testing "PENDING: unsupported board sizes"
    #_(is (= [1 2]
           (sut/unlock [1 2
                        3 4]
                       [1 2])))
    #_(is (= [1]
           (sut/unlock [1  2  3  4
                        5  6  7  8
                        9  10 11 12
                        13 14 15 16]
                       [1]))))
  (testing "bad boards"
    (is (nil? (sut/unlock (range 3) [1])) "must be square"))
  (testing "illegal moves"
    (is (nil? (unlock [2 1 1])) "can't have repeats, consecutive")
    (is (nil? (unlock [1 2 1])) "can't have repeats, any which way")
    (is (nil? (unlock [1 2])) "too short, must satisfy length requirements")
    (is (nil? (unlock [0 1 2])) "OOB, can't contain zero")
    (is (nil? (unlock [8 9 10])) "OOB, can't move beyond board range")
    (is (nil? (unlock [])) "empty moves")))

;; Misc testing of rules (incomplete)

(deftest uniqueness
  (is (sut/moves-non-overlapping? [1 2]))
  (is (not (sut/moves-non-overlapping? [1 2 1]))))

(deftest individual-moves
  (testing "legal moves"
    (is (sut/legal-move-9? 1 2) "basic one-over")
    (is (sut/legal-move-9? 1 6) "knight"))
  (testing "illegal moves"
    (is (not (sut/legal-move-9? 1 3)) "can't hop in row")
    (is (not (sut/legal-move-9? 1 9)) "can't hop diagonally")))
