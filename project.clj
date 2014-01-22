(defproject talendar "0.1.1"
  :description "working with quil"
  :url "https://github.com/tangrammer/talendar"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [quil "1.6.0"]
                 [clj-time "0.5.0"]

                                        ; [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 ]
  :jvm-opts ["-Xmx3g" "-server"]


  :resource-paths ["lib/gstreamer-java.jar" "lib/GSVideo.jar" "lib/jna.jar" "lib/gstreamer/macosx64"]
  )
