import{J as o,r as t,j as s,B as h,c as p}from"./index-ztVTG9lp.js";import{S as R}from"./index-CxrYC0hI.js";import{S as E}from"./index-CUoWmka9.js";import"./index-CFriIEhO.js";import"./Portal-Q4ncUo3s.js";import"./useId-z-Hxf0DT.js";import"./Overflow-BJnMDnoT.js";import"./motion-Cigbmjr9.js";import"./PurePanel-wLdZvyoL.js";import"./useLocale-u_29S4ue.js";import"./useIcons-CeeGwwsM.js";import"./CheckOutlined-B5MpEWXu.js";const V=()=>{const u=[{label:"최신순",value:o.LATEST},{label:"추천순",value:o.POPULAR}],[d,m]=t.useState([]),[i,x]=t.useState(0),[f,g]=t.useState(!0),[n,v]=t.useState(o.LATEST),[l,S]=t.useState(1),[T,c]=t.useState(!1);console.log(d);const b=async()=>{const e="/api/trip-review/allTripReview";c(!0);try{const a=(await p.get(`${e}?orderType=${n}&pageNumber=${l}`)).data;return console.log("여행기 리스트 조회",a),a.code==="200 성공"&&(g(a.data.hasMore),m(y=>[...y,...a.data.reviews])),a}catch(r){return console.log("여행기 리스트 조회",r),null}finally{c(!1)}},j=async()=>{try{const r=(await p.get("/api/trip-review/allTripReviewCount")).data;return x(r.data),r}catch(e){return console.log("여행기 개수",e),null}},w=()=>{S(e=>e+1)};return t.useEffect(()=>{b()},[n,l]),t.useEffect(()=>{j()},[]),s.jsxs("div",{className:"flex flex-col",children:[s.jsxs("section",{className:"flex justify-between items-center px-4 py-4 border-b border-t border-slate-100",children:[s.jsxs("p",{className:"font-semibold text-sm text-slate-700",children:["총 ",i==null?void 0:i.toLocaleString(),"건"]}),s.jsx(R,{options:u,defaultValue:o.LATEST,variant:"borderless",className:"text-sm text-slate-700",onChange:e=>v(e)})]}),s.jsxs("section",{children:[s.jsx(E,{spinning:T}),s.jsx("div",{className:"flex items-center justify-center",children:f&&s.jsx(h,{className:`px-5 py-4 border border-slate-300 \r
        rounded-3xl text-base text-slate-600`,onClick:w,children:"더보기"})})]})]})};export{V as default};
