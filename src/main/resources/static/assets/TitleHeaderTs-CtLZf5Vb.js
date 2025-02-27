import{r,j as e}from"./index-CG8vYf2y.js";import{a as x,b as i}from"./index-CWOPntzi.js";const f=({icon:a="back",title:t="",onClick:o=()=>{},rightContent:n=null})=>{const[c,s]=r.useState(!1);return r.useEffect(()=>{const l=()=>{window.scrollY>0?s(!0):s(!1)};return window.addEventListener("scroll",l),()=>{window.removeEventListener("scroll",l)}},[]),e.jsxs("div",{className:`
        max-w-3xl w-full mx-auto h-[60px]
        flex  items-center justify-between 
        px-[16px] 
        sticky top-0 left-0 z-10 
        transition-colors duration-100 ${c?"bg-white":"bg-transparent"}`,children:[e.jsxs("div",{className:"flex gap-[12px] items-center",children:[e.jsx("button",{type:"button",className:"text-3xl text-slate-700",onClick:o,children:a==="back"?e.jsx(x,{}):e.jsx(i,{})}),e.jsx("div",{className:"text-lg font-bold text-slate-700",children:t?`${t}`:""})]}),e.jsx("div",{className:"flex items-center text-3xl text-slate-700",children:n})]})};export{f as T};
