import{r as o,J as a}from"./index-Ca07tLEd.js";import{u as c}from"./useForceUpdate-D47g7Lp2.js";import{u as f}from"./responsiveObserver-C_hLadzf.js";function b(){let s=arguments.length>0&&arguments[0]!==void 0?arguments[0]:!0;const e=o.useRef({}),t=c(),r=f();return a(()=>{const n=r.subscribe(u=>{e.current=u,s&&t()});return()=>r.unsubscribe(n)},[]),e.current}export{b as u};
