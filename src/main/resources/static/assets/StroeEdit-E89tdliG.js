import{g as f,h as D,u as N,k as T,a2 as E,r as d,j as a,B as U,m as k,w as g}from"./index-B5mutXqH.js";import{s as B}from"./strfAtom-DskG5uTT.js";import{b as R}from"./match-CC9Jzjkz.js";import{u as A,F as l}from"./index-BEI7oVHT.js";import{S as C}from"./index-BY3lugu9.js";import{U as O}from"./index-B_xZz7fT.js";import"./index-Dwc3GK1e.js";import"./index-BJiJYRrR.js";import"./index-CYfWfzdt.js";import"./index-BQGSIoXt.js";import"./index-5wnY7uQU.js";import"./index-DRs9Aw0K.js";import"./motion-Bx9cQuU0.js";import"./responsiveObserver-D_pW8k_-.js";import"./zoom-6e5oPkw1.js";import"./Portal-Cl124Xm8.js";import"./index-DiAQL4ZR.js";import"./index-DUI94dea.js";import"./index-BAYCThZ_.js";import"./useId-DwjxQd62.js";import"./roundedArrow-DZNwFq69.js";import"./useLocale-CwtI8rOT.js";import"./fade-CxB0NvTB.js";import"./CheckOutlined-3VCDWbSU.js";const ut=()=>{const h=f("accessToken"),n=f("user").strfDtos[0].busiNum;console.log(n);const[p]=D(),i=p.get("strfId"),u=p.get("category"),x=N(),P=()=>{x(`/business/store?strfId=${i}&category=${u}&tab=0`)},[j,z]=T(B),w=j.strfPics.map((t,e)=>({uid:`-${e}`,name:t.strfPic,status:"done",url:`${E}/${i}/${t.strfPic}`})),[o,y]=d.useState(w),[b,c]=d.useState(!1),[$]=A(),F=async t=>{console.log("form",t);const e={strfId:i,busiNum:n},s=new FormData;if(!o||o.length===0){console.error("fileList가 비어 있습니다!");return}s.append("p",new Blob([JSON.stringify(e)],{type:"application/json"})),(o.length??!1)&&o.forEach(r=>{s.append("strfPic",r.originFileObj)}),await S(s)},I=({fileList:t})=>{y(t)},L=async t=>{let e=t.url;e||(e=await new Promise(v=>{const m=new FileReader;m.readAsDataURL(t.originFileObj),m.onload=()=>v(m.result)}));const s=new Image;s.src=e;const r=window.open(e);r==null||r.document.write(s.outerHTML)},S=async t=>{const e="/api/detail/strf/pic";c(!0);try{const s=await k.put(`${e}?strfId=${i}&busiNum=${n}`,t,{headers:{Authorization:`Bearer ${h}`}}),r=s.data;return r.code==="200 성공"&&(c(!1),g.success("사진 변경을 성공했습니다"),P()),console.log("사진 변경",s.data),r}catch(s){return console.log("사진 변경",s),g.error("사진 변경에 실패했습니다"),c(!1),null}};return a.jsx("div",{children:a.jsx(C,{spinning:b,children:a.jsxs(l,{form:$,onFinish:F,name:"strfPic",children:[a.jsx(l.Item,{name:"strfPic",rules:[{required:!0,message:"메뉴 사진을 등록해주세요.",validator:()=>o.length>0?Promise.resolve():Promise.reject(new Error("메뉴 사진을 등록해주세요."))}],help:`${R(u)} 이미지는 다수(최대 5장)을 등록하실 수 있습니다.`,children:a.jsx(O,{listType:"picture-card",fileList:o,onChange:I,onPreview:L,beforeUpload:()=>!1,accept:"image/*",maxCount:5,children:o.length<5&&"+ Upload"})}),a.jsx(l.Item,{children:a.jsx(U,{type:"primary",htmlType:"submit",size:"large",className:"text-lg",children:"등록하기"})})]})})})};export{ut as default};
