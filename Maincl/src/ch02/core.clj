(defn or [x];don't work
  (cond (some true? x) true
        (= 2 (count x)) (second x)

        (>= 2 (count (remove false? x))) (second (remove false? x))

        (not= instance? Boolean x) x
    )
  )
(defn and [x];don't work
  (cond (some false? x) false

        (= 2 (count (filter (fn [y] (not= y false)) x))) (second x)
        (not= instance? Boolean x) x
        )
  )
(defn not [x];works
  (cond (some true? x) false
        (some false? x) true)
  )
(defn ds [m l]
  (map (fn [i]
         (if (seq? i)
           (ds m i)
           (m i i)))
       l))

