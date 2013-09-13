class DojoBindArgs {
    Map args
    DojoBindArgs(Map map) {
        args = map
    }
    String toString() {
        StringBuilder bindArgs = new StringBuilder("{")
        args.each { k, v ->
            if (bindArgs.length() > 1) {
                bindArgs << ","
            }
            switch (k) {
            case ["url","mimetype","method","transport"]:
                bindArgs << k << ":'" << v << "'"
                break
            case "content":
                bindArgs << k << ':'
                if (v instanceof Map) {
                    bindArgs << '{'
                    boolean first = true
                    v.each { ck, cv ->
                        if (first) {
                            first = false
                        } else {
                            bindArgs << ','
                        }
                        bindArgs << ck << ":dojo.byId('" << cv << "').value"
                    }
                    bindArgs << '}'
                } else {
                    bindArgs << v
                }
                break
            default:
                bindArgs << k << ':' << v
            }
        }
        bindArgs << '}'
        return bindArgs.toString()
    }
}