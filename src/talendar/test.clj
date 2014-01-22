(ns talendar.test
  (:use quil.core)
  (:import [javax.swing JFileChooser]
           [codeanticode.gsvideo GSMovie]

           )
  )
(declare example )

(def movie (atom nil))

(defn get-movie []
  @movie
  )

(defn setup []

  (reset! movie (GSMovie. example "./data/palmera.mov"))



  )

(defn draw []



  (when-not (nil? @movie)
    (image (get-movie) 100 100))
  )

; this method is hard coded on quil/applet.clj
(defn movieEvent [movie]
  (println "listen (movie) event!" movie)
  (.read movie)
  )

(def new-frame (atom 0))

(defn keyed []
  (when-not (.isSeeking (get-movie))
      (condp = (raw-key)
        \q  (when (> @new-frame 0)
              (swap! new-frame dec))
        \w (when (< @new-frame  (.length (get-movie)))
             (swap! new-frame inc))
        \x (do (println "pdfing") (let [pdf (create-graphics 300 300 :pdf  "./hola.pdf")
                                        ]
                                    (begin-record :pdf "hola.pdf")
                                    ))
        \o (do
               (.play (get-movie) )
               (.goToBeginning (get-movie))
               (.pause (get-movie)))
        \z (end-record)

        "nothing")
      (.play (get-movie))
      (.jump (get-movie) 1)
      (.pause (get-movie))
      )

  )

(defsketch example
  :title "j"
  :setup setup
  :draw draw
  :size [300 300]
  :target :perm-frame
  :key-pressed keyed
;  :mouse-pressed raton
  :movie-event movieEvent
  )
