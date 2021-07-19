// Copyright 2007 Google Inc. All Rights Reserved.
//
/**
 * @fileoverview Support for inserting and updating images in Writely.
 *
 * @author attila@google.com (Attila Bodis)
 */

/**
 * Pseudo-namespace for Writely; create if it doesn't already exist.
 * @type {Object}
 */
var writely = (typeof writely == 'undefined') ? {} : writely;

/**
 * Returns true iff the specified object has the named attribute defined; false
 * otherwise.
 *
 * @param {Object} object the object to test
 * @param {String} attribute the name of the attribute to look for
 * @return {Boolean} true iff the object has the named attribute defined
 */
writely.isDefined = function(object, attribute) {
  return !!(object && typeof object[attribute] != 'undefined');
};

/**
 * Temporary stub for browser support info in Writely pseudo-namespace.
 * TODO (sullivan): move this to a more appropriate place.
 * @type {Object}
 */
writely.browser = (typeof writely.browser == 'undefined') ? {} : writely.browser;
writely.browser.isIE = ((navigator.userAgent.indexOf("msie") != -1) &&
                        (navigator.userAgent.indexOf("opera") == -1) &&
                        (navigator.userAgent.indexOf("webtv")== -1) &&
                        !window.opera) ? true : false;

/**
 * Tests whether the specified object has the named attribute defined; if so,
 * returns the value of the attribute (which could be null).  Otherwise, returns
 * the specified default value, if any, or null as a last resort.
 *
 * @param {Object} object the object to test
 * @param {String} attribute the name of the attribute to look for
 * @param {Object} opt_default the value to return if no such attribute is
 *    defined; defaults to null
 * @return {Object} the value of the specified attribute, or the default
 */
writely.getAttribute = function(object, attribute, opt_default) {
  opt_default = opt_default || null;
  return writely.isDefined(object, attribute) ? object[attribute] : opt_default;
};

/**
 * Pseudo-namespace for image support in Writely; create if it doesn't already
 * exist.
 * @type {Object}
 */
writely.image = (typeof writely.image == 'undefined') ? {} : writely.image;

/**
 * Map of key-value pairs where the keys are image URLs and the values are
 * timeout IDs.  Used by #getNaturalDimensions and #getDimensionsFromSrc during
 * the asynchronous loading of sizer images.
 * @type {Object}
 */
writely.image.timeouts = (typeof writely.image.timeouts == 'undefined') ? {}
    : writely.image.timeouts;

/**
 * Takes a reference to an existing, completely loaded image element in the DOM,
 * and returns an object containing its properties.  The returned object
 * contains the following name-value pairs:
 *  <ul>
 *    <li>{String} src - the source URL of the image</li>
 *    <li>{Number} naturalHeight - the natural height of the image, in
 *      pixels</li>
 *    <li>{Number} naturalWidth - the natural width of the image, in
 *      pixels</li>
 *    <li>{String} height - the current height of the image, as determined by
 *      its CSS height or legacy height attribute</li>
 *    <li>{String} width - the current width of the image, as determined by
 *      its CSS width or legacy width attribute</li>
 *    <li>{Boolean} link - true iff the image is wrapped in an anchor element
 *      that links to a full-size version of the image</li>
 *    <li>{HTMLAnchorElement} a - reference to an enclosing anchor element
 *      that links the image to a full-size version of itself, if any</li>
 *    <li>{Boolean} wrap - true iff the image is floated left or right and
 *      text is allowed to wrap around it</li>
 *    <li>{String} align - horizontal alignment; one of 'left', 'center', or
 *      'right'</li>
 *    <li>{HTMLElement} div - reference to an enclosing DIV element that is used
 *      to align the image left, center, or right, if any</li>
 *  </ul>
 *
 * @param {HTMLImageElement} image reference to an image element in the DOM
 * @return {Object} object containing both the natural and the curretn height
 *    and width of the image; see above for details
 */
writely.image.getProperties = function(image) {

  if (image) {

    var properties = {};

    // The image source.
    properties.src = image.src;

    // Natural dimensions.
    if (writely.isDefined(image, 'naturalWidth') && writely.isDefined(image,
        'naturalHeight')) {
      // Gecko.
      properties.naturalWidth = image.naturalWidth;
      properties.naturalHeight = image.naturalHeight;
    } else {
      // IE.
      var cssWidth = image.style.width;
      var cssHeight = image.style.height;
      var legacyWidth = image.width;
      var legacyHeight = image.height;
      image.style.width = '';
      image.style.height = '';
      image.removeAttribute('width');
      image.removeAttribute('height');
      properties.naturalWidth = image.width;
      properties.naturalHeight = image.height;
      image.style.width = cssWidth || legacyWidth;
      image.style.height = cssHeight || legacyHeight;
    }

    // Current dimensions (CSS trumps legacy attributes).
    properties.width = image.style.width || (writely.browser.isIE ?
        image.currentStyle.width : image.getAttribute('width'));
    properties.height = image.style.height || (writely.browser.isIE ?
        image.currentStyle.height : image.getAttribute('height'));

    // Determine whether the image is linked to a full-size version of itself
    // or not by finding the nearest anchor ancestor (if any) and comparing its
    // href attribute to the src attribute of the image.
    properties.link = false;
    for (var node = image.parentNode;
         node && node != node.ownerDocument.body;
         node = node.parentNode) {
      if (node.tagName && node.tagName == 'A') {
        if (node.href == image.src) {
          // There is an enclosing anchor element that links to the image.
          properties.link = true;
          properties.a = node;
        }
        // Only look at the nearest anchor ancestor.
        break;
      }
    }

    // Determine alignment and text wrapping properties.
    properties.wrap = true;
    if ((image.style.cssFloat && image.style.cssFloat != '')
        || (image.style.styleFloat && image.style.styleFloat != '')) {
      var cssFloat = image.style.cssFloat || image.style.styleFloat;
      if (cssFloat) {
        cssFloat = cssFloat.toLowerCase();
      }
      if (cssFloat == 'left' || cssFloat == 'right') {
        // The image is floated left or right using CSS, allowing text to wrap
        // around it.
        properties.align = cssFloat;
        properties.wrap = true;
      }
    } else if (image.align && image.align != '') {
      var align = image.align.toLowerCase();
      if (align == 'left' || align == 'right') {
        // The image is floated left or right using the legacy align attribute,
        // allowing text to wrap around it.
        properties.align = align;
        properties.wrap = true;
      }
    } else {
      // Determine whether the image is left-, right-, or center-aligned by
      // finding the nearest DIV ancestor and looking at its text-align
      // attribute.
      for (var node = image.parentNode;
           node && node != node.ownerDocument.body;
           node = node.parentNode) {
        if (node.tagName && node.tagName == 'DIV') {
          if (node.style.textAlign && node.style.textAlign != '') {
            var textAlign = node.style.textAlign.toLowerCase();
            if (textAlign == 'left' || textAlign == 'right'
                || textAlign == 'center') {
              properties.align = textAlign;
              properties.div = node;
              properties.wrap = false;
            }
          }
          // Only look at the nearest DIV ancestor.
          break;
        }
      }
    }

    return properties;

  } else {
    // Nonexistent or incomplete image.
    return null;
  }
};

/**
 * Takes a reference to an {@link Image} object, and returns an object
 * containing the dimensions of the referenced image.  The returned object
 * contains 2 name-value pairs:
 *  <ul>
 *    <li><code>{Number} naturalHeight</code> - the natural height of the image,
 *      in pixels</li>
 *    <li><code>{Number} naturalWidth</code> - the natural width of the image,
 *      in pixels</li>
 *  </ul>
 * The image must be fully loaded (as reported by its <code>complete</code>
 * property) in order for this function to work.
 *
 * @param {Image} image a completely loaded Image object
 * @return {Object} object containing the natural height/width of the image;
 *    see above for details
 */
writely.image.getNaturalDimensions = function(image) {
  var dimensions = {};
  if (image) {
    // IE7 assumes some strange icon size unless you clear it
    image.removeAttribute('width');
    image.removeAttribute('height');
    image.style.height = 'auto';
    image.style.width = 'auto';
    dimensions.naturalWidth = image.naturalWidth || image.width;
    dimensions.naturalHeight = image.naturalHeight || image.height;
  }
  return dimensions;
};

/**
 * Function to be used as the onload event handler for sizer images.
 *
 * @param {Image} image reference to the image to be sized
 * @param {Function} callback reference to the callback function to be called
 *    once sizing is complete
 */
writely.image.onLoadHandler = function(image, callback) {
  if (writely.isDefined(writely.image, 'timeouts')
      && writely.image.timeouts[image.src]) {
    window.clearTimeout(writely.image.timeouts[image.src]);
    writely.image.timeouts[image.src] = null;
    delete writely.image.timeouts[image.src];
  }
  callback(writely.image.getNaturalDimensions(image));
};

/**
 * Takes a string containing the source of an image and a reference to a
 * callback function, and calls the callback function as soon as the natural
 * dimensions of the referenced image have been determined.  The object passed
 * to the callback function contains 2 name-value pairs:
 *  <ul>
 *    <li><code>{Number} naturalHeight</code> - the natural height of the image,
 *      in pixels</li>
 *    <li><code>{Number} naturalWidth</code> - the natural width of the image,
 *      in pixels</li>
 *  </ul>
 * Since the image in question has to be fully loaded into the browser before
 * its dimensions can be reliably determined, this function has to work
 * asynchronously.
 *
 * @param {String} src image source
 * @param {Function} callback callback function to be called once the image has
 *    been loaded and auto-sized
 */
writely.image.getDimensionsFromSrc = function(src, callback) {
  if (src && callback) {
    var sizer = new Image();
    sizer.onload = sizer.onerror = sizer.onabort = function() {
      writely.image.onLoadHandler(sizer, callback);
    };
    sizer.src = src + (src.indexOf('?') != -1 ? '&t=' : '?t=')
        + (new Date()).getTime();
    writely.image.timeouts[sizer.src] = window.setTimeout(function() {
      writely.image.onLoadHandler(sizer, callback);
    }, 10000);
  }
};

/**
 * Takes an object containing properties like the ones described above (see
 * {@link #getProperties}), creates a node (with possible children) that
 * corresponds to those properties, and returns it.
 */
writely.image.getDocumentFragmentFromProperties = function(properties) {

  // The element we'll serialize and insert into the editor.
  var elem = null;

  if (properties) {

    // Create the <img> element.
    var img = document.createElement('img');
    img.src = properties.src;
    elem = img;
    if (properties.width) {
      img.style.width = properties.width;
    }
    if (properties.height) {
      img.style.height = properties.height;
    }

    // If the user checked the "link to original image" checkbox, we have to
    // wrap the image in an <a> element.
    if (properties.link) {
      var a = document.createElement('a');
      a.target = '_blank';
      a.href = properties.src;
      a.appendChild(elem);
      elem = a;
    }

    // If the image is to be floated left or right, we can get away with using
    // the cssFloat property; otherwise we have to wrap the whole thing in a
    // <div>.
    if (img.style.width && img.style.width == '100%') {
      // Always wrap in a DIV.
      var div = document.createElement('div');
      div.style.textAlign = 'center';
      div.appendChild(elem);
      elem = div;
      // Add some padding.
      elem.style.paddingTop = '1em';
      elem.style.paddingBottom = '1em';
      elem.style.paddingRight = '1em';
      elem.style.paddingLeft = '1em';
    } else if (properties.wrap
        && (properties.align == 'left' || properties.align == 'right')) {
      img.style.cssFloat = properties.align;
      img.style.styleFloat = properties.align;
      // Add some margins.
      img.style.marginTop = '1em';
      img.style.marginBottom = '0';
      img.style.marginLeft = (properties.align == 'left') ? '0' : '1em';
      img.style.marginRight = (properties.align == 'right') ? '0' : '1em';
    } else {
      var div = document.createElement('div');
      div.style.textAlign = properties.align;
      div.appendChild(elem);
      elem = div;
      // Add some padding.
      elem.style.paddingTop = '1em';
      elem.style.paddingBottom = '1em';
      elem.style.paddingRight = '0';
      elem.style.paddingLeft = '0';
    }

    // We have to give the element a (hopefully) unique ID.
    elem.id = 'img_' + Math.random().toString().substring(2);

  }

  return elem;

};
