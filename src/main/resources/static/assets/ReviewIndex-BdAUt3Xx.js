import{c as I,g as v,r,j as e,a7 as w,B as N,m as R}from"./index-CclgMCKg.js";import{R as k}from"./ReviewItem-i0go3S5j.js";import{S as y}from"./StrfInfo-C5iIDPjw.js";import{N as M}from"./NoData-CVZyKg5f.js";import{S as b}from"./index-DzJzb7gE.js";import"./index-317k7Gzh.js";import"./BottomSheet-nkPRIX3f.js";import"./proxy-luPhEEJa.js";import"./swiper-react-BiZMhr8u.js";import"./index-coMdXwgz.js";import"./index-CSlYUxik.js";import"./index-Dx4kxOlF.js";import"./Portal-cEDniDzA.js";import"./useId-C98JHZMn.js";import"./isMobile-DjGTsQxe.js";import"./motion-BtiDeT7c.js";import"./roundedArrow-S-OOpRVx.js";import"./zoom-BWmeO6-E.js";import"./index-DK5P-bAW.js";const U=()=>{var p;const[f]=I(),u=Number(f.get("strfId")),d=v("accessToken"),[s,a]=r.useState([]),[i,l]=r.useState(0),[g,n]=r.useState(!1),[h,j]=r.useState(!0),m=async o=>{const c="/api/business/review/all";n(!0);try{const t=(await R.get(`${c}?start_idx=${i}&page_size=20`,{headers:{Authorization:`Bearer ${d}`}})).data;return console.log("리뷰 목록 조회",t),t&&o==="more"&&a([...s,...t]),t&&o==="delete"&&(l(0),a(t)),t[t.length-1].isMore===!1&&j(!1),n(!1),t}catch(x){return console.log("리뷰 목록 조회",x),n(!1),null}},S=async()=>{l(i+20)};return r.useEffect(()=>{m("more")},[i]),e.jsxs("div",{className:"flex flex-col gap-5",children:[e.jsx(y,{}),e.jsxs(b,{spinning:g,children:[e.jsx("section",{className:"flex flex-col gap-10 pb-10",children:((p=s[0])==null?void 0:p.reviewId)!==null?s==null?void 0:s.map((o,c)=>e.jsx(k,{item:o,strfId:u,setReviewList:a,getReviewList:m},c)):e.jsx(M,{icon:e.jsx(w,{}),content:"리뷰가 없습니다."})}),h&&e.jsx("div",{className:"flex justify-center",children:e.jsx(N,{variant:"outlined",className:"px-5 py-1 h-[9.6vw] max-h-[50px] text-xl text-slate-400 rounded-[32px]",onClick:S,children:"더보기"})})]})]})};export{U as default};
