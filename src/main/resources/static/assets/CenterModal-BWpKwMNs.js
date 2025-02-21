import{r as o,j as e}from"./index-Ci2k4qFh.js";import{B as l}from"./button-wUgNsQBt.js";const i=({handleClickCancle:t,handleClickSubmit:n,content:r=null})=>{const s=()=>{t()},x=a=>{a.stopPropagation()};return e.jsx("div",{className:`fixed top-0 left-[50%] translate-x-[-50%] z-10\r
                max-w-3xl w-full mx-auto h-screen\r
                flex items-center justify-center\r
                bg-[rgba(0,0,0,0.5)]\r
                `,onClick:()=>{s()},children:e.jsxs("div",{className:`bg-white \r
                    rounded-2xl px-[60px] py-[30px]\r
                    flex flex-col items-center justify-center\r
                    gap-[20px]\r
                    `,onClick:x,children:[e.jsx("div",{children:e.jsx("p",{className:"text-slate-700",children:r})}),e.jsxs("div",{className:"flex gap-[20px]",children:[e.jsx(l,{color:"default",variant:"filled",htmlType:"button",className:`px-[15px] py-[20px] text-[20px] text-slate-400 font-semibold\r
            w-full`,onClick:t,children:"취소"}),e.jsx(l,{type:"primary",htmlType:"button",className:`px-[15px] py-[20px] text-[20px] text-white font-semibold\r
            w-full`,onClick:n,children:"확인"})]})]})})},d=o.memo(i);export{d as C};
