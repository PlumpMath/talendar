(ns talendar.core
  (:use [quil.core]
        [talendar.toda :as toda])
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie])
  )


(declare example movie)
(def  img (atom nil))
(def click (atom [-1 -1]))
(defn setup []
    (text-size 10)
  (let [fc (JFileChooser.)
        rv (.showOpenDialog fc example) ]
    (when (zero? rv)
      (let [file (.getSelectedFile fc)]
        (reset! img (load-image "/Users/juan_mini/load.png"))
        )))
  (comment (def movie (GSMovie. example "./data/station.mov"))
           (.loop movie ))

  )

(def size-w 300)
(def size-h 300)
(defn draw []
  (frame-rate 20)

  (ellipse 100 100 30 30)
  (fill 0)
  (rect 0 0 300 300)

  (stroke 250)
  (let [r-w (quot size-w 6)
        r-h (quot size-h 6)
        jan (first (toda/get-year-data 2014))]
    (let [data  (for [x (range 6)
                      y (range 6)]
                  [x y]

                  )]
      (doall (map (fn [[x y]]
              (let [xx (* x r-w)
                    yy (* y r-h)
                    w (+ xx r-w)
                    h (+ yy r-h)
                    cl @click
                    ]
                (fill 0)
                (if (and (>= (cl 0) xx)
                         (<= (cl 0) w)
                         (>= (cl 1) yy)
                         (<= (cl 1) h))
                  (do
                    (fill 255 255 0 40)
                    (rect xx yy w h)
                    (text-day (+ (* y 6) (inc x )) (+ xx (/ r-w 2)) (+  yy (/ r-h 2)))
                    )
                  (do
                    (fill 0)
                    (rect xx yy w h))
                  )
                )) data))
      ))
  (comment
    (when-not (nil? @img)
                                        ;    (image @img 0 0)
      )
    (when (.available movie)
      (.read movie))
    (image movie 100 100))


  )

(defn text-day [n x y]
  (push-style)
  (fill 255 0 0)
  (text (str n) x y)
  (pop-style)
  )

(defn movieEvent [e]
  (println "aaaa")
  (.read movie)
  )

(defn raton []
  (reset! click [(mouse-x) (mouse-y)])
  (comment (let [fc (JFileChooser.)
         rv (.showOpenDialog fc example) ]
     (when (zero? rv)
       (let [file (.getSelectedFile fc)]
         (reset! img (load-image (.getPath file)))
         ))))
  (println @click)

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [size-w size-h]
  :mouse-pressed raton
  :movie-event movieEvent
  )
