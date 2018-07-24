/**
 * 
 * @author xu_jin
 * @version 
 */
function arrayToJson(o) {
	var r = [];
	if (typeof o == "string"){
		return "\""
		+ o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
				.replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	}

	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + arrayToJson(o[i]));
			if (!!document.all
					&& !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
							.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for ( var i = 0; i < o.length; i++) {
				r.push(arrayToJson(o[i]));
			}
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
}

Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    symbol = symbol !== undefined ? symbol : "";
    thousand = thousand || ",";
    decimal = decimal || ".";
    var number = this,
        negative = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
};

