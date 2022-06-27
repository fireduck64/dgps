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

java_binary(
  name = "CorrectPath",
  main_class = "CorrectPath",
  runtime_deps = [
    ":dgpslib",
  ],
)

java_binary(
  name = "AddPath",
  main_class = "AddPath",
  runtime_deps = [
    ":dgpslib",
  ],
)

java_binary(
  name = "AddBasePoint",
  main_class = "AddBasePoint",
  runtime_deps = [
    ":dgpslib",
  ],
)
java_binary(
  name = "Conv",
  main_class = "Conv",
  runtime_deps = [
    ":dgpslib",
  ],
)
java_binary(
  name = "FindAvg",
  main_class = "FindAvg",
  runtime_deps = [
    ":dgpslib",
  ],
)

