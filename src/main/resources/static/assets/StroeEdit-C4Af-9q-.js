import{g as f,b as v,u as D,N as T,U,r as d,j as a,B as E,c as B,M as g}from"./index-ztVTG9lp.js";import{s as R}from"./strfAtom-CQNEEJ42.js";import{m as k}from"./match-D4UHT3tw.js";import{u as A,F as l}from"./index-DB63C6vj.js";import{S as C}from"./index-CUoWmka9.js";import{U as M}from"./index-CpUo7_Fg.js";import"./index-BNRq6L-3.js";import"./index-D6Qu_HcJ.js";import"./index-DQQnbW7l.js";import"./index-CAQOQu-_.js";import"./index-BJLpRm29.js";import"./motion-Cigbmjr9.js";import"./collapse-BbEVqHco.js";import"./zoom-CZRi_VRg.js";import"./Portal-Q4ncUo3s.js";import"./responsiveObserver-s_X0FfS_.js";import"./index-BoQfDuur.js";import"./index-Cyve9Zid.js";import"./index-CFriIEhO.js";import"./useId-z-Hxf0DT.js";import"./roundedArrow-BEsDa3vf.js";import"./useLocale-u_29S4ue.js";import"./fade-6OldJHuR.js";import"./useForceUpdate-DrZMuEBj.js";import"./CheckOutlined-B5MpEWXu.js";const ft=()=>{const h=f("accessToken"),n=f("user").strfDtos[0].busiNum;console.log(n);const[p]=v(),i=p.get("strfId"),u=p.get("category"),x=D(),P=()=>{x(`/business/store?strfId=${i}&category=${u}&tab=0`)},[j,_]=T(R),y=j.strfPics.map((t,e)=>({uid:`-${e}`,name:t.strfPic,status:"done",url:`${U}/${i}/${t.strfPic}`})),[o,w]=d.useState(y),[b,c]=d.useState(!1),[$]=A(),F=async t=>{console.log("form",t);const e={strfId:i,busiNum:n},s=new FormData;if(!o||o.length===0){console.error("fileList가 비어 있습니다!");return}s.append("p",new Blob([JSON.stringify(e)],{type:"application/json"})),(o.length??!1)&&o.forEach(r=>{s.append("strfPic",r.originFileObj)}),await S(s)},I=({fileList:t})=>{w(t)},L=async t=>{let e=t.url;e||(e=await new Promise(N=>{const m=new FileReader;m.readAsDataURL(t.originFileObj),m.onload=()=>N(m.result)}));const s=new Image;s.src=e;const r=window.open(e);r==null||r.document.write(s.outerHTML)},S=async t=>{const e="/api/detail/strf/pic";c(!0);try{const s=await B.put(`${e}?strfId=${i}&busiNum=${n}`,t,{headers:{Authorization:`Bearer ${h}`}}),r=s.data;return r.code==="200 성공"&&(c(!1),g.success("사진 변경을 성공했습니다"),P()),console.log("사진 변경",s.data),r}catch(s){return console.log("사진 변경",s),g.error("사진 변경에 실패했습니다"),c(!1),null}};return a.jsx("div",{children:a.jsx(C,{spinning:b,children:a.jsxs(l,{form:$,onFinish:F,name:"strfPic",children:[a.jsx(l.Item,{name:"strfPic",rules:[{required:!0,message:"메뉴 사진을 등록해주세요.",validator:()=>o.length>0?Promise.resolve():Promise.reject(new Error("메뉴 사진을 등록해주세요."))}],help:`${k(u)} 이미지는 다수(최대 5장)을 등록하실 수 있습니다.`,children:a.jsx(M,{listType:"picture-card",fileList:o,onChange:I,onPreview:L,beforeUpload:()=>!1,accept:"image/*",maxCount:5,children:o.length<5&&"+ Upload"})}),a.jsx(l.Item,{children:a.jsx(E,{type:"primary",htmlType:"submit",size:"large",className:"text-lg",children:"등록하기"})})]})})})};export{ft as default};
