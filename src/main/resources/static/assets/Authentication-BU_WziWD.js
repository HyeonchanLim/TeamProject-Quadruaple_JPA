import{r,u as B,a as q,j as s,b as F,I as j,B as E,m as p,X as d}from"./index-CclgMCKg.js";/* empty css                    */import{F as n}from"./index-Y7Vl51Ml.js";import"./motion-BtiDeT7c.js";import"./collapse-BbEVqHco.js";import"./zoom-BWmeO6-E.js";import"./Portal-cEDniDzA.js";import"./useBreakpoint-C1fBIcAO.js";import"./index-BbTjQyva.js";import"./index-CSlYUxik.js";import"./index-Dx4kxOlF.js";import"./useId-C98JHZMn.js";import"./isMobile-DjGTsQxe.js";import"./roundedArrow-S-OOpRVx.js";import"./useLocale-drylJvgs.js";const W=()=>{var b;const[l,v]=r.useState(""),[c,C]=r.useState(""),[a,x]=r.useState(null),[f,h]=r.useState(!1),[N,g]=r.useState(!1),[u,y]=r.useState(!1);r.useEffect(()=>{console.log("error",u)},[u]);const m=B(),o=(b=q().state)==null?void 0:b.userType,I=async()=>{try{const e=await p.get(`/api/mail?email=${l}`);return console.log(e.data),e.data}catch(e){return console.log("error :",e),null}},S=async({email:e,code:t})=>{try{const i=await p.post("/api/mail",{email:e,code:t});return console.log(i.data),i.data}catch(i){return console.log(i),null}},w=async e=>{try{const t=await p.get(`/api/user/sign-up?email=${e}`);return g(t.data.data),t.data.data===!0?d.success("사용 가능한 이메일입니다"):(d.error("이미 사용중인 이메일입니다"),y(!0)),t.data.data}catch(t){return console.log(t),d.error("이메일 중복 확인 중 오류가 발생했습니다"),g(!1),!1}};r.useEffect(()=>{if(a!==null&&a>0){const e=setInterval(()=>{x(t=>t!==null&&t>0?t-1:0)},1e3);return()=>clearInterval(e)}else a===0&&h(!0)},[a]);const T=async()=>{await S({email:l,code:c})&&(o==="user"?m("/signup/user",{state:{email:l,userType:o}}):o==="business"&&m("/signup/business",{state:{email:l,userType:o}}))},k=async e=>{const t=e.target.value;t&&/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(t)&&await w(t)},$=async()=>{x(300),h(!1),await I()};return s.jsxs("div",{children:[s.jsx(F,{title:"이메일 인증",icon:"back",onClick:()=>m(-1)}),s.jsxs(n,{autoComplete:"off",layout:"vertical",className:"mt-6 px-4",onFinish:T,children:[s.jsxs(n.Item,{label:"이메일",className:"custom-form-item !text-xs",rules:[{required:!0,message:"Please input!"}],children:[s.jsx(j,{placeholder:"이메일을 입력하세요",className:"py-[14px] px-3",value:l,onBlur:k,onChange:e=>{v(e.target.value),e.target.value.trim()!==""&&y(!1)},status:u?"error":void 0}),s.jsx(E,{type:"primary",className:"text-xs font-medium inline-block px-4 !h-auto",onClick:$,disabled:!N,children:"인증코드 받기"})]}),s.jsxs(n.Item,{label:"인증코드",rules:[{required:!0,message:f?"입력 시간이 초과되었습니다":"인증 코드를 다시 입력해주세요"}],className:"custom-form-item !text-xs mt-3",children:[s.jsx(j,{className:"py-[14px] px-3 relative",value:c,onChange:e=>C(e.target.value),disabled:f||a===null}),s.jsx("div",{className:"text-primary text-base absolute top-1/2 -translate-y-1/2 right-4 ",children:a!==null?a>0?`${Math.floor(a/60)}:${String(a%60).padStart(2,"0")}`:"시간 초과":""})]}),s.jsxs("p",{className:"text-xs text-slate-400",children:["메일을 받지 못했다면 인증 코드 재전송을 요청하거나 스팸 메일함을 확인해 보세요. 이메일이 오지 않았나요?"," ",s.jsx("span",{className:"text-slate-600 underline cursor-pointer",children:"이메일 재발송"})]}),s.jsx(n.Item,{children:s.jsx(E,{type:"primary",htmlType:"submit",className:"text-base py-3 !h-auto w-full mt-6",disabled:!l||!c,children:"이메일 인증"})})]})]})};export{W as default};
