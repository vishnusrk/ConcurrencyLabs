load("@npm//:defs.bzl", "npm_link_all_packages")
load("@npm//ui:vite/package_json.bzl", vite_bin = "bin")

npm_link_all_packages(name = "node_modules")

vite_bin.vite(
    name = "bundle",
    srcs = [
        "index.html",
        "package.json",
        "tsconfig.json",
        "vite.config.ts",
        ":node_modules",
    ] + glob(["src/**"]),
    chdir = package_name(),
    out_dirs = ["dist"],
    args = ["build"],
    visibility = ["//visibility:public"],
)