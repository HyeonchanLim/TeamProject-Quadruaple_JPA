import{k,r as s,u as E,i as C,j as e,a1 as P,m as R}from"./index-LV_9s5-1.js";import"./TitleHeader-6cQeiO5x.js";import{S as $}from"./SearchItems-DijOlg_u.js";import{T as V}from"./api-RMrr9BxD.js";import"./userAtom-Bt2xL0hP.js";import{e as T}from"./EditTripAtom-CA9kZbe-.js";import{S as B}from"./Skeleton-DaH0fQ42.js";import"./index-CoXfSCcE.js";import"./index-Ds3yrZWO.js";import"./index-CF7lad59.js";import"./index-DdrXLXVe.js";import"./searchAtom-DskBk_2_.js";import"./match-5XfzEgGS.js";import"./index-D6sixpAC.js";import"./index-ChJ6LYmi.js";import"./index-CdvDs16G.js";import"./index-DXJ_gz2Q.js";import"./index-C_ByFtLp.js";import"./index-BsJ66pFJ.js";import"./index-DN57V0sg.js";import"./index-Btv3waz5.js";import"./Portal-CiuFLH_K.js";import"./useId-LGEjO-HJ.js";import"./motion-D8x5Mwwf.js";import"./roundedArrow-D1YheW33.js";import"./zoom-Bx4l_5D1.js";const pt=()=>{var f,h;const[l,g]=k(T);s.useEffect(()=>{console.log("편집 데이터",l)},[l]);const p=E(),a=C().state,r=a==null?void 0:a.from;a==null||a.tripLocationList;const L=()=>{r?r==="/schedule/index"?(p(r),g({...l,tripLocationList:o})):p(r,{state:o}):p("/schedule/days",{state:{selectedLocationId:o,title:o[0].title}})},[c,j]=s.useState(null),[o,x]=s.useState([]),[q,b]=s.useState(!1),[m,y]=s.useState(""),[N,S]=s.useState("");s.useState(!1),s.useEffect(()=>{console.log("selectedLocationId",o)},[o]);const d=s.useRef(null);s.useEffect(()=>{d.current&&console.log(d.current)},[]);const I=async()=>{try{const t=await R.get(V.getLocationList);j(t.data)}catch(t){console.log("지역 목록 조회:",t)}};s.useEffect(()=>{I()},[]),s.useEffect(()=>{if(c&&(l!=null&&l.tripLocationList)){const t=c.data.locationList.filter(i=>l.tripLocationList.includes(i.locationId));x(t)}},[c,l==null?void 0:l.tripLocationList]);const u=c==null?void 0:c.data.locationList,n=u==null?void 0:u.filter(t=>t.title.includes(m)),v=t=>{o.some(i=>i.locationId===t.locationId)||x([...o,t])},w=t=>{x(o.filter(i=>i.locationId!==t.locationId))};return e.jsxs("div",{children:[e.jsx($,{searchValue:m,setSearchValue:y,setSearchState:b,inputValue:N,setInputValue:S}),e.jsx("ul",{className:"flex flex-col gap-[20px] px-[32px] mb-[20px]",children:(n==null?void 0:n.length)>0?n==null?void 0:n.map(t=>e.jsxs("li",{className:"flex justify-between items-center",children:[e.jsxs("div",{className:"flex gap-[30px] items-center",children:[e.jsx("div",{className:"w-[100px] h-[100px] rounded-2xl overflow-hidden",children:t.locationPic?e.jsx("img",{src:`${P}/${t.locationPic}`,alt:t.title,ref:d,className:"w-full h-full object-cover"}):e.jsx(B.Image,{active:!1,style:{width:"100px",height:"100px"}})}),e.jsxs("div",{className:"flex flex-col gap-[16px]",children:[e.jsx("p",{className:"text-[24px] text-slate-700",children:t.title}),e.jsx("p",{className:"text-[18px] text-slate-500",children:"어디론가 떠나고 싶을 때"})]})]}),e.jsx("div",{className:"h-auto flex items-center justify-center ",children:o.filter(i=>i.locationId===t.locationId).length>0?e.jsx("button",{type:"button",className:"text-[16px] text-primary border border-primary3 rounded-2xl px-[15px] py-[5px]",onClick:()=>w(t),children:"취소"}):e.jsx("button",{type:"button",className:"text-[16px] text-slate-500 border border-slate-300 rounded-2xl px-[15px] py-[5px]",onClick:()=>v(t),children:"선택"})})]},t.locationId)):null}),e.jsx("div",{className:"w-full px-[32px] mb-[20px]",children:o.length>0?e.jsx("button",{type:"button",className:"w-full px-[20px] py-[15px] text-[20px] font-bold text-white bg-primary rounded-lg",onClick:L,children:o.length===1?`${(f=o[0])==null?void 0:f.title} 선택 완료`:`${(h=o[0])==null?void 0:h.title} 외 ${o.length-1}개 선택 완료`}):e.jsx("button",{type:"button",className:"w-full px-[20px] py-[15px] text-[20px] font-bold text-slate-400 bg-slate-50 rounded-lg",children:"도시 선택"})})]})};export{pt as default};
