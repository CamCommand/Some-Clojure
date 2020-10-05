(ns main)

(def p1 '(and x x (or x (and y (not z)))))
(def p2 '(and (and z false) (or x true false)))
(def p3 '(or true a))

(def m1 '{x false, z true})
(def m2 '{x false, y false})

(def l1 '(or y x true))
(def l2 '(or x true))
(def l3 '(or x false))
(def l4 '(or false y))
(def l5 '(or false false true true false))
(def l6 '(or false false false))
(def l7 '(or x y false))
(def l8 '(or x y z))

(defn nornot [x]
  (conj (drop 1 x) "nor")
  )

(defn norand

  )

(defn noror

  )



(defn dropfirst [ylist]
  (drop 1 ylist)
  )

(defn getsymbols [zlist]
  (filter symbol? (dropfirst zlist)
          )
  )

(defn orsimplify [x]                                        ;work
  (let [x (distinct (remove true? x))]
    (cond
      (some true? x) true
      (= () (getsymbols x)) false
      ;(some seq? x) (distinct x)
      (= 1 (count x)) (first (distinct x))
      :else (distinct (conj x 'or))
      ))
  )
(defn andsimplify [x]                                       ;works
  (let [x (distinct (remove true? x))]
    (cond (some false? x) false
          (every? true? (dropfirst x)) true
          ;(some seq? x) (remove true? (distinct x))
          (= () (getsymbols x)) (first (distinct (dropfirst x)))
          (= 1 (count x)) (first (distinct x))
          :else (distinct (conj x 'and))
          ))
  )
(defn notsimplify [x]                                       ;works
  (cond (some true? x) false
        (some false? x) true
        :else x)
  )
(defn ds [m l]
  (map (fn [i]
         (if (seq? i)
           (ds m i)
           (m i i)))
       l))

(defn bindsimplify [hmap list]
  (do
    (let [list (ds hmap list)]                              ;working
      (cond
        (= 'and (first list)) (andsimplify (map (fn [i]
                                                  (if (seq? i)
                                                    (bindsimplify '{} i)
                                                    i))
                                                list))
        (= 'or (first list)) (orsimplify (map (fn [i]
                                                (if (seq? i)
                                                  (bindsimplify '{} i)
                                                  i))
                                              list))
        (= 'not (first list)) (notsimplify (map (fn [i]
                                                  (if (seq? i)
                                                    (bindsimplify '{} i)
                                                    i))
                                                list))
        )
      ))
  )