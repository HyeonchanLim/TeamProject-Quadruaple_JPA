import{w as i,$ as o,a0 as g}from"./index-Ci2k4qFh.js";function f(r){var n=arguments.length>1&&arguments[1]!==void 0?arguments[1]:{},t=[];return i.Children.forEach(r,function(e){e==null&&!n.keepEmpty||(Array.isArray(e)?t=t.concat(f(e)):o(e)&&e.props?t=t.concat(f(e.props.children,n)):t.push(e))}),t}function c(r,n){var t=Object.assign({},r);return Array.isArray(n)&&n.forEach(function(e){delete t[e]}),t}const y=function(r){if(!r)return!1;if(r instanceof Element){if(r.offsetParent)return!0;if(r.getBBox){var n=r.getBBox(),t=n.width,e=n.height;if(t||e)return!0}if(r.getBoundingClientRect){var a=r.getBoundingClientRect(),u=a.width,s=a.height;if(u||s)return!0}}return!1},d=r=>{const n=i.useContext(g);return i.useMemo(()=>r?typeof r=="string"?r??n:r instanceof Function?r(n):n:n,[r,n])};export{y as i,c as o,f as t,d as u};
