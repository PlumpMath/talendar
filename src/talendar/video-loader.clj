(ns talendar.video-loader
  (:use [quil.core :only (map-range)]
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
      (.jump movie  (first map-frames-number))
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
        (println (. movie frame) )
        (swap! frames conj (clone-image movie))
        (if (< (count @frames) (count numbers))
          (do
            (.play movie)
            (let [to-frame (numbers (count @frames))]
              (if (< (.length movie) to-frame)
                (.goToEnd movie)
                (.jump movie  to-frame))
              )
            true
            )
          false
          )
        true
      )
      true
      )
    )
  false

)
