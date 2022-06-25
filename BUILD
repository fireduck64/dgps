package(default_visibility = ["//visibility:public"])

java_library(
  name = "dgpslib",
  srcs = glob(["src/**/*.java", "src/*.java"]),
  deps = [
    "@duckutil//:duckutil_lib",
    "@maven//:net_minidev_json_smart",
    "@maven//:com_google_guava_guava",
  ],
)

java_binary(
  name = "BaseCapture",
  main_class = "BaseCapture",
  runtime_deps = [
    ":dgpslib",
  ],
)

java_binary(
  name = "MobileCapture",
  main_class = "MobileCapture",
  runtime_deps = [
    ":dgpslib",
  ],
)

