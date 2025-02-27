import{l as g,a8 as H,a9 as J,J as h,_ as s,aa as K,r as l,ab as R,ac as L,Q as _,o as E,ad as M,H as Q,v as x,K as U,ae as V}from"./index-CG8vYf2y.js";function k(n){var e=arguments.length>1&&arguments[1]!==void 0?arguments[1]:{},o=[];return g.Children.forEach(n,function(r){r==null&&!e.keepEmpty||(Array.isArray(r)?o=o.concat(k(r)):H(r)&&r.props?o=o.concat(k(r.props.children,e)):o.push(r))}),o}function S(n){var e;return n==null||(e=n.getRootNode)===null||e===void 0?void 0:e.call(n)}function W(n){return S(n)instanceof ShadowRoot}function q(n){return W(n)?S(n):null}function G(n){return n.replace(/-(.)/g,function(e,o){return o.toUpperCase()})}function X(n,e){J(n,"[@ant-design/icons] ".concat(e))}function N(n){return h(n)==="object"&&typeof n.name=="string"&&typeof n.theme=="string"&&(h(n.icon)==="object"||typeof n.icon=="function")}function I(){var n=arguments.length>0&&arguments[0]!==void 0?arguments[0]:{};return Object.keys(n).reduce(function(e,o){var r=n[o];switch(o){case"class":e.className=r,delete e.class;break;default:delete e[o],e[G(o)]=r}return e},{})}function T(n,e,o){return o?g.createElement(n.tag,s(s({key:e},I(n.attrs)),o),(n.children||[]).map(function(r,t){return T(r,"".concat(e,"-").concat(n.tag,"-").concat(t))})):g.createElement(n.tag,s({key:e},I(n.attrs)),(n.children||[]).map(function(r,t){return T(r,"".concat(e,"-").concat(n.tag,"-").concat(t))}))}function A(n){return K(n)[0]}function j(n){return n?Array.isArray(n)?n:[n]:[]}var Y=`
.anticon {
  display: inline-flex;
  align-items: center;
  color: inherit;
  font-style: normal;
  line-height: 0;
  text-align: center;
  text-transform: none;
  vertical-align: -0.125em;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.anticon > * {
  line-height: 1;
}

.anticon svg {
  display: inline-block;
}

.anticon::before {
  display: none;
}

.anticon .anticon-icon {
  display: block;
}

.anticon[tabindex] {
  cursor: pointer;
}

.anticon-spin::before,
.anticon-spin {
  display: inline-block;
  -webkit-animation: loadingCircle 1s infinite linear;
  animation: loadingCircle 1s infinite linear;
}

@-webkit-keyframes loadingCircle {
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}

@keyframes loadingCircle {
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}
`,Z=function(e){var o=l.useContext(R),r=o.csp,t=o.prefixCls,i=o.layer,a=Y;t&&(a=a.replace(/anticon/g,t)),i&&(a="@layer ".concat(i,` {
`).concat(a,`
}`)),l.useEffect(function(){var u=e.current,C=q(u);L(a,"@ant-design-icons",{prepend:!i,csp:r,attachTo:C})},[])},nn=["icon","className","onClick","style","primaryColor","secondaryColor"],m={primaryColor:"#333",secondaryColor:"#E6E6E6",calculated:!1};function en(n){var e=n.primaryColor,o=n.secondaryColor;m.primaryColor=e,m.secondaryColor=o||A(e),m.calculated=!!o}function on(){return s({},m)}var d=function(e){var o=e.icon,r=e.className,t=e.onClick,i=e.style,a=e.primaryColor,u=e.secondaryColor,C=_(e,nn),y=l.useRef(),f=m;if(a&&(f={primaryColor:a,secondaryColor:u||A(a)}),Z(y),X(N(o),"icon should be icon definiton, but got ".concat(o)),!N(o))return null;var c=o;return c&&typeof c.icon=="function"&&(c=s(s({},c),{},{icon:c.icon(f.primaryColor,f.secondaryColor)})),T(c.icon,"svg-".concat(c.name),s(s({className:r,onClick:t,style:i,"data-icon":c.name,width:"1em",height:"1em",fill:"currentColor","aria-hidden":"true"},C),{},{ref:y}))};d.displayName="IconReact";d.getTwoToneColors=on;d.setTwoToneColors=en;function z(n){var e=j(n),o=E(e,2),r=o[0],t=o[1];return d.setTwoToneColors({primaryColor:r,secondaryColor:t})}function rn(){var n=d.getTwoToneColors();return n.calculated?[n.primaryColor,n.secondaryColor]:n.primaryColor}var tn=["className","icon","spin","rotate","tabIndex","onClick","twoToneColor"];z(M.primary);var w=l.forwardRef(function(n,e){var o=n.className,r=n.icon,t=n.spin,i=n.rotate,a=n.tabIndex,u=n.onClick,C=n.twoToneColor,y=_(n,tn),f=l.useContext(R),c=f.prefixCls,p=c===void 0?"anticon":c,B=f.rootClassName,P=Q(B,p,x(x({},"".concat(p,"-").concat(r.name),!!r.name),"".concat(p,"-spin"),!!t||r.name==="loading"),o),v=a;v===void 0&&u&&(v=-1);var $=i?{msTransform:"rotate(".concat(i,"deg)"),transform:"rotate(".concat(i,"deg)")}:void 0,O=j(C),b=E(O,2),F=b[0],D=b[1];return l.createElement("span",U({role:"img","aria-label":r.name},y,{ref:e,tabIndex:v,onClick:u,className:P}),l.createElement(d,{icon:r,primaryColor:F,secondaryColor:D,style:$}))});w.displayName="AntdIcon";w.getTwoToneColor=rn;w.setTwoToneColor=z;function cn(n,e){var o=Object.assign({},n);return Array.isArray(e)&&e.forEach(function(r){delete o[r]}),o}const sn=function(n){if(!n)return!1;if(n instanceof Element){if(n.offsetParent)return!0;if(n.getBBox){var e=n.getBBox(),o=e.width,r=e.height;if(o||r)return!0}if(n.getBoundingClientRect){var t=n.getBoundingClientRect(),i=t.width,a=t.height;if(i||a)return!0}}return!1},ln=n=>{const e=g.useContext(V);return g.useMemo(()=>n?typeof n=="string"?n??e:n instanceof Function?n(e):e:e,[n,e])};export{w as I,q as g,sn as i,cn as o,k as t,ln as u};
