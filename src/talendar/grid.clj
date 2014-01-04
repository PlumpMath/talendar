(ns talendar.grid
  (:use [quil.core]))
(def size-w 1600)
(def size-h 1000)
(def r-w (quot size-w 6))
(def r-h (quot size-h 6))
(def data-36-days   (for [x (range 6)
                          y (range 6)]
                      [x y]))

(defn coords [x y]
  (let [ xx (* x r-w)
        yy (* y r-h)
        w (+ xx r-w)
        h (+ yy r-h)]
    { :xx xx :yy yy :w w :h h}
    )

  )

(defn is-target? [current-rect current-click]
  "@deprecated!!!"
  (and (>= (current-click 0) (:xx current-rect))
       (<= (current-click 0) (:w current-rect))
       (>= (current-click 1) (:yy current-rect))
       (<= (current-click 1) (:h current-rect))))
(defn paint [current-rect]
  "returns { :xx xx :yy yy :w w :h h}"
  (apply rect (vals current-rect))
  )

(defn get-rect-number
  "1 indexed "
  ([x y]
     (+ (* y 6) (inc x )))
  ([[x y]] (get-rect-number x y))
  )
