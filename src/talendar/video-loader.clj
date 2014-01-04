(ns talendar.video-loader
  (:use [quil.core :only (map-range image)]
        [talendar.helper])
  (:import [codeanticode.gsvideo GSMovie]))


(defn set-up [sketch url  limit]
  (let [ movie (GSMovie. sketch url)
        frames (atom [])]
    (.play movie)
    (.goToBeginning movie)
    (.pause movie)

    (println "first frame" (. movie frame) )
    (println "finished setup" (.length movie ) )
    (let [
          map-frames-number (vec (map #(int (map-range % 0 limit 0 (.length movie ) ))
                                      (range 1 (inc limit))
                                      ))]

      (.play movie)
      (.jump movie  (int (first map-frames-number)))
      (.pause movie)

      {:movie movie :number map-frames-number :frames frames})

    )

  )

(defn is-loading-movie? [movie frames numbers]
  "frames is atom type"
  (if (< (count @frames)  (count numbers))
    (if (and (.available movie) (.ready movie))
      (do
        (.read movie)
        ;(println "reading frame:::" (. movie frame) )
        (let [r (clone-image movie)]
          (swap! frames conj r)
          (when (< 1 (count @frames))
              (image (last (butlast @frames)) 300 0 300 300 ))
          (image (last @frames) 300 300 300 300 )
          (println (count @frames))
          )

        (if (< (count @frames) (count numbers))
          (do
            (.play movie)
            (let [to-frame (numbers (count @frames))]
              (if (< (.length movie) to-frame)
                (.goToEnd movie)
                (.jump movie  (int to-frame)))
              )
            true
            )
          true
          )
        true

        (do
          (println "unavalibale" (.available movie) (.ready movie))

          true))
      )
    false
    )

  )
