import{r as i,u as c,j as e,T as x}from"./index-Cwx4I7Vi.js";import{T as m}from"./index-C8O5cC_t.js";import"./CloseOutlined-GwKmxF7X.js";import"./EllipsisOutlined-nfpQXzsS.js";import"./index-B7BtatMF.js";import"./Portal-SJsC_TZ1.js";import"./useId-cPQzkJ8H.js";import"./KeyCode-DNlgD2sM.js";import"./Overflow-CYHc8NuW.js";import"./Keyframes-C9Oo5sJi.js";const d=({category:s})=>{const t=c(),a=l=>{t(`/chatroom?roomId=${l}`)};return console.log(s),e.jsx(e.Fragment,{children:e.jsx("ul",{className:"flex flex-col",children:e.jsxs("li",{className:"flex items-center justify-between gap-3 px-4 py-4 cursor-pointer",onClick:()=>a("1"),children:[e.jsxs("div",{className:"flex items-center gap-3",children:[e.jsx("div",{className:"w-14 h-14 bg-slate-200 flex items-center justify-center rounded-2xl overflow-hidden",children:e.jsx("img",{src:"#",alt:""})}),e.jsxs("div",{className:"flex flex-col gap-[5px]",children:[e.jsxs("p",{className:"text-lg text-slate-700 font-semibold",children:["이름 ",e.jsx("span",{className:"text-slate-300",children:"다인원"})]}),e.jsx("p",{children:"마지막 채팅"})]})]}),e.jsxs("div",{className:"flex flex-col items-end justify-between",children:[e.jsx("p",{className:"text-sm text-slate-400",children:"현재-마지막 채팅 시각"}),e.jsx("p",{className:"px-3 py-[5px] bg-primary rounded-2xl text-white text-sm",children:"1"})]})]})})})},p=i.memo(d),k=()=>{const s=c(),t=()=>{s(-1)},[a,l]=i.useState("전체"),r=[{key:"전체",label:e.jsx("p",{className:"text-lg",children:"전체"})},{key:"예약문의",label:e.jsx("p",{className:"text-lg",children:"예약문의"})},{key:"여행톡",label:e.jsx("p",{className:"text-lg",children:"여행톡"})}],n=o=>{l(o)};return e.jsxs("div",{children:[e.jsx(x,{title:"채팅",icon:"back",onClick:t}),e.jsxs("div",{children:[e.jsx(m,{defaultActiveKey:"전체",items:r,onChange:n,className:"px-[16px]"}),e.jsx(p,{category:a})]})]})};export{k as default};
