import{r as a,j as e}from"./index-DB3eQjaF.js";import{B as l}from"./button-x43GSXMA.js";const i=({handleClickCancle:t,handleClickSubmit:n,content:s=null})=>{const x=()=>{t()},r=o=>{o.stopPropagation()};return e.jsx("div",{className:`fixed top-0 left-[50%] translate-x-[-50%] z-10\r
                max-w-3xl w-full mx-auto h-screen\r
                flex items-center justify-center\r
                bg-[rgba(0,0,0,0.5)]\r
                `,onClick:()=>{x()},children:e.jsxs("div",{className:`bg-white \r
                    rounded-2xl px-[60px] py-[30px]\r
                    flex flex-col items-center justify-center\r
                    gap-[20px]\r
                    `,onClick:r,children:[e.jsx("div",{children:e.jsx("p",{className:"text-slate-700",children:s})}),e.jsxs("div",{className:"flex gap-[20px]",children:[e.jsx(l,{color:"default",variant:"filled",htmlType:"button",className:`px-[15px] py-[20px] text-[20px] text-slate-400 font-semibold\r
            w-full`,onClick:t,children:"취소"}),e.jsx(l,{type:"primary",htmlType:"button",className:`px-[15px] py-[20px] text-[20px] text-white font-semibold\r
            w-full`,onClick:n,children:"확인"})]})]})})},f=a.memo(i),c=({handleClickCancle:t,handleClickSubmit:n,modalContent:s=null,showButton:x=!0})=>{const r=()=>{t()},o=p=>{p.stopPropagation()};return e.jsx("div",{className:`fixed top-0 left-[50%] translate-x-[-50%] z-10\r
                max-w-3xl w-full mx-auto h-screen\r
                flex items-end justify-center\r
                bg-[rgba(0,0,0,0.5)]\r
                `,onClick:()=>{r()},children:e.jsxs("div",{className:`bg-white w-full \r
                    rounded-t-2xl px-[60px] py-[55px]\r
                    flex flex-col gap-[20px]\r
                    mb-[100px]\r
                    `,onClick:o,children:[s,x?e.jsxs("div",{className:"flex gap-[20px]",children:[e.jsx(l,{color:"default",variant:"filled",htmlType:"button",className:`px-[15px] py-[20px] text-[24px] text-slate-400 font-semibold\r
            w-full`,onClick:t,children:"취소"}),e.jsx(l,{type:"primary",htmlType:"button",className:`px-[15px] py-[20px] text-[24px] text-white font-semibold\r
            w-full`,onClick:n,children:"확인"})]}):null]})})},u=a.memo(c);export{u as B,f as D};
