//中文
Function.prototype.registerMethod = function (name, func) {
	this.prototype[name] = func;
    return this;
};

Function.registerMethod('inherits', function (parent) {
    var d = 0, p = (this.prototype = new parent());
    this.registerMethod('superMethod', function (name) {
        var f, r, t = d, v = parent.prototype;
        if (t) {
            while (t) {
                v = v.constructor.prototype;
                t -= 1;
            }
            f = v[name];
        } else {
            f = p[name];
            if (f == this[name]) {
                f = v[name];
            }
        }
        d += 1;
        r = f.apply(this, Array.prototype.slice.apply(arguments, [1]));
        d -= 1;
        return r;
    });
    return this;
});