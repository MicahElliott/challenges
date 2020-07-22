(ns mde.word-search-test
  (:require [mde.word-search :as sut]
            [clojure.test :as t :refer [deftest is testing]]
            [clojure.string :as str]))

(defn rand-str
  [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

(defn gen-mat
  "Generate a random matrix for later fun."
  []
  (str/join "\n" (take 8 (repeatedly #(rand-str 8)))))

;; For today, just go with the existing sut/board.
(def mat nil)

(deftest word-finding
  (testing "single- and multi-word presence"
    (is (= 2 (sut/count-word-in-matrix mat "HELLO")))
    (is (= 1 (sut/count-word-in-matrix mat "WORLD")))
    (is (= 2 (sut/count-word-in-matrix mat "BUZZ")))
    )
  (testing "non-presence"
    (is (= 0 (sut/count-word-in-matrix mat "CLOJURE")))
    (is (= 0 (sut/count-word-in-matrix mat "COWABUNGA")))))
