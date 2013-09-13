import java.text.DateFormat

class JSONCoder {
    static stringEncodable = [Character, String, URL]
	static stringFormatters = [(Date):DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)]
    static hiddenFields = ["version"]

    def object

    JSONCoder(obj) {
        object = obj
    }

    String toString() {
        StringBuilder sb = new StringBuilder()
        encodeJSON(sb, object, "")
        return sb.toString()
    }

    void encodeArray(StringBuilder sb, array, path) {
//System.err.println("encodeArray('${sb}', ${array})")
        sb.append("[")
        boolean first = true
        array.each {
            if (!first) {
                sb.append(",")
            } else {
                first = false
            }
            encodeJSON(sb, it, path)
        }
        sb.append("]")
    }

    void encodeMap(StringBuilder sb, map, path) {
//System.err.println("encodeMap('${sb}', ${map})")
        sb.append("{")
        boolean first = true
        map.each { k, v ->
            if (!first) {
                sb.append(",")
            } else {
                first = false
            }
            sb.append(k)
            sb.append(':')
            encodeJSON(sb, v, "${path}.${k}" )
        }
        sb.append("}")
    }

    void encodeObject(StringBuilder sb, o, path) {
//System.err.println("encodeObject('${sb}', ${o})")
		def props = o.properties.findAll { k, v ->
//			!k.equals("class") && !k.equals("metaClass") && !k.equals("constraints") && !k.equals("hasMany") && !k.equals("belongsTo")
			!k.equals("metaClass") && !k.equals("constraints") && !k.equals("hasMany") && !k.equals("belongsTo")
    	}
        encodeMap(sb, props, path)
    }

    void encodeToString(StringBuilder sb, o) {
//System.err.println("encodeToString('${sb}', ${o})")
        sb.append('"')
        def s = o.toString().replaceAll('\n','\\n')
        def oldlen = sb.length()
        sb.append(s)
        if (s.indexOf('\\') != -1) {
            i = sb.indexOf('\\', oldlen)
            while (i != -1) {
                sb.insert(i, '\\')
                i = sb.indexOf('\\', i + 2)
            }
        }
		if (s.indexOf('"') != -1) {
		    i = sb.indexOf('"', oldlen)
		    while (i != -1) {
		        sb.insert(i, '\\')
		        i = sb.indexOf('"', i + 2)
		    }
		}
//        sb.append(o.toString().replaceAll("\\", "\\\\").replaceAll("\"", "\\\""))
        sb.append('"')
    }

    void encodeJSON(StringBuilder sb, o, path) {
//System.err.println("encodeJSON('${sb}', ${o})")
        if (o == null) {
            sb.append("null")
        } else if (o instanceof Number) {
            sb.append(o)
        } else if (o instanceof Boolean) {
            sb.append(o)
        } else if ( stringEncodable.find { it.isAssignableFrom(o.getClass()) } ) {
            encodeToString(sb, o)
        } else if (o instanceof Calendar) {
            encodeJSON(sb, o.time, path)
        } else if (o instanceof Class) {
            encodeJSON(sb, o.name, path)
        } else {
            def formatType = stringFormatters.keySet().find { it.isAssignableFrom(o.getClass()) }
            if ( formatType ) {
	            def formatter = stringFormatters[formatType]
				encodeToString(sb, formatter.format(o))
	        } else if (o instanceof Collection || o.getClass().array) {
	            if (path.length() == 0) {
	            	encodeArray(sb, o, path)
	            } else {
	                encodeToString(sb,"-skipped-")
	            }
	        } else if (o instanceof Map) {
	            encodeMap(sb, o, path)
	        } else {
//System.err.println(o.class.name)
//System.err.println(o.properties.keySet())
	            encodeObject(sb, o, path)
	        }
        }
    }
}
