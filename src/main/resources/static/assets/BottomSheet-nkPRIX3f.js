import{r as n,j as t}from"./index-CclgMCKg.js";import{m as r}from"./proxy-luPhEEJa.js";const m=({open:l,onClose:a,actions:o,title:i=""})=>(n.useEffect(()=>(l?document.body.style.overflow="hidden":document.body.style.overflow="auto",()=>{document.body.style.overflow="auto"}),[l]),l?t.jsx(r.div,{tabIndex:-1,className:"max-w-[768px] w-full left-1/2 -translate-x-1/2 fixed inset-0 bg-black/50 flex justify-center items-end z-[9999] overflow-hidden",initial:{opacity:0},animate:{opacity:1},exit:{opacity:0},onClick:()=>a(),children:t.jsxs(r.div,{className:" bg-white w-full rounded-t-3xl py-5 shadow-lg",initial:{y:"100%"},animate:{y:0},exit:{y:"100%"},transition:{type:"spring",stiffness:300,damping:30},drag:"y",dragConstraints:{top:0,bottom:100},dragElastic:.2,onDragEnd:(e,s)=>{s.offset.y>100&&a()},onClick:e=>e.stopPropagation(),children:[t.jsx("div",{className:"w-12 h-1 bg-slate-400 rounded-full mx-auto mb-4"}),t.jsxs("div",{children:[i&&t.jsx("p",{className:"text-2xl font-semibold text-slate-700 px-7 py-4",children:i}),o==null?void 0:o.map((e,s)=>t.jsx("button",{className:"w-full text-left text-lg text-slate-500 p-4 border-b last:border-none hover:bg-gray-100",onClick:e.onClick,children:e.label},s))]})]})}):t.jsx(t.Fragment,{}));export{m as B};
