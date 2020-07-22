# Coding Challenges

This suite of challenge questions presently contains solutions to the
following:

- word search
- phone grid pattern unlocking

This project was generated with:

```sh
clj -Anew app mde/challenges
```

## Installation

Download from https://GitHub.com/HicahElliott/challenges

## Usage

Run the project and all its modules directly:

	❯ clojure -m run-all

    WORD SEARCH
	===========
	The word HELLO appears in this matrix 2 times.
	AOTDLROW
	LCBMUMLU
	DRUJDBLJ
	PAZHZZEF
	BCZELFHW
	RKULVPPG
	ALBLPOPQ
	BEMOPPJY

	UNLOCKING
	=========
	The unlock pattern [1 2 3] is legal for the standard 3x3 grid.

Run the project's tests.

    $ clojure -A:test:runner

Build an uberjar:

    $ clojure -A:uberjar

## TODO

- add to circleci
- generate docs with [codox](https://github.com/weavejester/codox)

## License

Copyright © 2020 Micah Elliott
