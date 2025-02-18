import{r as o,c as W,j as e,d as oe,B as Y,e as _,g as le,R as ae,u as re,z as ce,L as ie}from"./index-lSgSIVq0.js";import{T as de,R as xe}from"./TitleHeader-CH0dT_zw.js";import{c as V}from"./index-CL3h7xgQ.js";import{u as pe,a as z,D as ue,c as me,b as K,v as G,S as H,K as he,P as ge}from"./ScheduleDay-DO87eHQj.js";import{t as fe}from"./tripAtom-BVcRk0I1.js";import{a as ye}from"./MarkerClusterer-C3I5xS5p.js";import{d as A}from"./dayjs.min-CBrWkGb5.js";/* empty css                    */import"./UserTrips-B4C_anov.js";import"./swiper-react-85fzHbBl.js";/* empty css               */import{c as je,n as be,D as Ie,a as ke}from"./emotion-styled.browser.esm-CtjZ6w8c.js";import{F as m}from"./index-DmAzEE9E.js";import{C as J}from"./index-D5aFR8fN.js";import"./index-xOiyq3gC.js";import"./match-Bwf83gmQ.js";import"./index-DOmuDq51.js";import"./pic-Jp7D6aQ0.js";import"./CenterModal-BSzHFepq.js";import"./index-QJhHY3lT.js";import"./index-76rm2S88.js";import"./index-BITOSm_t.js";import"./Portal-DgKfOFff.js";import"./useId-NRmY-Uhn.js";import"./zoom-JFcaoQzf.js";import"./Footer-UNjWO-rU.js";import"./collapse-BbEVqHco.js";import"./index-BJLQYX5H.js";import"./useLocale-B6HIg25i.js";import"./PurePanel-L0cLcqIW.js";import"./EllipsisOutlined-DQGc47Dl.js";import"./CheckOutlined-BpiEOwey.js";import"./Checkbox-CN-sUrkH.js";const{RangePicker:Q}=Ie;A.extend(je);const we=({tripData:s,handleClickCancle:h,getTrip:E})=>{var f;const[r]=m.useForm(),[g]=W(),P=parseInt(g.get("tripId")),L=()=>{h()},M=l=>{l.stopPropagation()},t=(l,a)=>{console.log("선택된 날짜:",l),console.log("포맷된 날짜:",a);const u=a==null?void 0:a.map(N=>N+":00");setDates(u)},p=l=>l&&l<A().endOf("day");be(Q)`
    .ant-picker-input input {
      color: "#334155" !important;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 0 10px;
      width: 130px;
    }
  `;const U=async l=>{try{const a=await _.patch("/api/trip",l);console.log("여행 수정",a.data),a.data&&(E(),h())}catch(a){console.log("여행 수정",a)}},F=l=>{var I;console.log(l);const{title:a,rangePicker:u,nowUser:N}=l,y=s!=null&&s.nowUser?s.nowUser.map(c=>c.userId).filter(c=>{var d;return!((d=l.nowUser)!=null&&d.includes(c))}):[],j=((I=l.nowUser)==null?void 0:I.filter(c=>!y.includes(c)))||[],b=u.map(c=>c.format("YYYY-MM-DD"));console.log(b);const $={title:a,trip_id:P,start_at:b[0],end_at:b[1],ins_user_list:j,del_user_list:y,ins_location_list:[...s==null?void 0:s.tripLocationList],del_location_list:[]};U($)};return e.jsx("div",{className:`fixed top-0 left-[50%] translate-x-[-50%] z-10\r
            max-w-3xl w-full mx-auto h-screen\r
            flex items-center justify-center\r
            bg-[rgba(0,0,0,0.5)]\r
            `,onClick:()=>{L()},children:e.jsx("div",{className:`bg-white \r
                rounded-2xl px-[60px] py-[30px]\r
                flex flex-col items-center justify-center\r
                gap-[20px]\r
                `,onClick:M,children:e.jsxs(m,{form:r,onFinish:F,requiredMark:!1,children:[e.jsx(m.Item,{name:"title",label:"제목",rules:[{required:!0,message:"제목을 입력해주세요."}],initialValue:s.title,children:e.jsx(oe,{})}),e.jsx(m.Item,{name:"rangePicker",label:"여행일자",rules:[{required:!0,message:"여행일자를 입력해주세요."}],initialValue:s!=null&&s.startAt&&(s!=null&&s.endAt)?[A(s.startAt),A(s.endAt)]:void 0,children:e.jsx(Q,{placeholder:["시작일 ","종료일"],disabledDate:p,variant:"borderless",onChange:t,separator:"~"})}),e.jsx(m.Item,{name:"nowUser",label:"참여자",initialValue:(s==null?void 0:s.tripUserIdList)||[],children:e.jsx(J.Group,{children:((f=s==null?void 0:s.tripUserIdList)==null?void 0:f.map((l,a)=>e.jsx(J,{value:l,children:l},a)))||null})}),e.jsxs(m.Item,{children:[e.jsx(Y,{color:"default",variant:"filled",htmlType:"button",onClick:h,children:"취소"}),e.jsx(Y,{type:"primary",htmlType:"submit",children:"수정"})]})]})})})},Ce=o.memo(we),Se={day:1,weather:"",schedules:[]},ve=()=>{le("accessToken");const[s,h]=ae(fe);o.useEffect(()=>{console.log("trip",s)},[s]);const[E]=W(),r=parseInt(E.get("tripId"));o.useEffect(()=>{h({...s,nowTripId:r})},[]);const g=re(),P=()=>{g(-1)},L=()=>{g(`/schedule/calculation?tripId=${r}`)},M=()=>{g(`/scheduleboard/schedulePost?tripId=${r}`)},[t,p]=o.useState({}),[U,F]=o.useState(!1),[f,l]=o.useState(t.title),[a,u]=o.useState(""),[N,y]=o.useState(!1);o.useState(!1);const[j,b]=o.useState({tripId:null,scheduleId:null,originDay:null,destDay:null,originSeq:null,destSeq:null});o.useEffect(()=>{console.log("여행 데이터",t)},[t]),o.useEffect(()=>{console.log("링크",a)},[a]),o.useEffect(()=>{console.log("title",f)},[f]);const $=o.useCallback(async()=>{try{const n=await _.get(`/api/trip/add-link?trip_id=${r}`);console.log(n.data),u(n.data.data)}catch(n){console.log("초대코드",n)}},[]),I=o.useCallback(async()=>{try{await navigator.clipboard.writeText(a),console.log("복사 성공")}catch(n){console.error("복사 실패:",n)}},[]),c=[{label:e.jsxs("div",{onClick:()=>I(),className:"flex flex-col gap-[10px] items-center justify-center",children:[e.jsx("p",{className:"bg-slate-100 px-[15px] py-[10px] rounded-lg text-slate-600",children:a}),e.jsxs("p",{className:"flex items-center gap-1 border-b border-slate-300",children:[e.jsx("i",{className:"text-slate-500",children:e.jsx(ye,{})}),e.jsx("span",{className:"text-slate-500",children:"초대코드 복사하기"})]})]}),key:"0"}],d=o.useCallback(async()=>{try{const n=await _.get(`/api/trip?trip_id=${r}&signed=true`);console.log("여행확인하기",n.data);const i=n.data.data;p(i),i&&F(!0)}catch(n){console.log(n)}},[]),X=o.useCallback(async n=>{try{const i=await _.patch("/api/schedule",n);console.log("일정 순서 변경 성공:",i.data),d()}catch(i){console.log("일정 순서 변경 실패:",i)}},[]),Z=()=>{y(!1)};o.useEffect(()=>{d(),t&&l(t.title)},[]);const T=t.days,D=pe(z(ge),z(he)),ee=n=>{const{active:i,over:q}=n;if(!q)return;const[x,k]=i.id.split("-").map(Number),[w,C]=q.id.split("-").map(Number);if(x===w&&k===C)return;const se=t.days.find(R=>R.day===x),te=t.days.find(R=>R.day===w),S=se.schedules[k],v=te.schedules[C],B={tripId:r,scheduleId:S.scheduleMemoId,originDay:x,destDay:w,originSeq:k+1,destSeq:C+1};b(B),console.log("드래그 앤 드롭 상세 정보:",{출발:{일차:x,순서:k,일정:{id:S.scheduleMemoId,제목:S.strfTitle||S.title,타입:S.scheOrMemo}},도착:{일차:w,순서:C,일정:v?{id:v.scheduleMemoId,제목:v.strfTitle||v.title,타입:v.scheOrMemo}:"마지막 위치"}});const O={...t},[ne]=O.days[x-1].schedules.splice(k,1);O.days[w-1].schedules.splice(C,0,ne),p(O),X(B)};return o.useEffect(()=>{j.tripId&&console.log("드래그 정보 상태 업데이트:",j)},[j]),e.jsxs("div",{children:[U?e.jsxs(e.Fragment,{children:[e.jsx(de,{icon:"back",onClick:P,rightContent:e.jsx(xe,{icon1:!1,icon2:!0,icon3Click:M,icon3:!0,icon4:!0})}),e.jsxs("div",{className:"flex flex-col gap-[30px] py-[30px]",children:[e.jsxs("div",{className:"mt-[60px] flex flex-col gap-[10px] px-[32px]",children:[e.jsxs("div",{className:"flex items-center justify-between",children:[e.jsxs("p",{className:"text-[18px] text-slate-700 ",children:[e.jsx("span",{children:t.startAt}),"-",e.jsx("span",{children:t.endAt})]}),e.jsx("button",{type:"button",onClick:()=>y(!0),children:e.jsx(ce,{className:"text-[24px] text-slate-300 bg-white"})})]}),e.jsx("h2",{className:"text-[36px] text-slate-700 font-bold",children:t.title}),e.jsx("div",{className:"flex items-center gap-3 mt-5"})]}),e.jsxs("div",{className:"flex items-center gap-[10px] px-[32px]",children:[e.jsx(ke,{menu:{items:c},trigger:["click"],overlayStyle:{marginTop:"10px"},children:e.jsxs("button",{type:"button",className:`flex items-center gap-[10px] \r
                  px-[15px] py-[10px] rounded-3xl\r
                  text-white bg-primary`,onClick:async()=>{await $(),I()},children:[e.jsx(V,{}),"초대 코드"]})}),e.jsxs("button",{type:"button",className:`flex items-center gap-[10px] \r
                px-[15px] py-[10px] rounded-3xl\r
                text-slate-500 bg-slate-100`,onClick:L,children:[e.jsx(V,{className:"text-slate-300"}),"가계부"]})]}),e.jsx("div",{className:"flex flex-col gap-[50px]",children:e.jsx(ue,{sensors:D,collisionDetection:me,onDragEnd:ee,children:T===null?e.jsx(K,{items:[{id:"1-0"}],strategy:G,children:e.jsx(H,{newTrip:!0,data:Se,startAt:t==null?void 0:t.startAt,tripId:r,getTrip:d,setTripData:p})}):T==null?void 0:T.map((n,i)=>e.jsx(K,{items:n.schedules.map((q,x)=>`${n.day}-${x}`),strategy:G,children:e.jsx(H,{newTrip:!0,data:n,startAt:t==null?void 0:t.startAt,tripId:r,getTrip:d,setTripData:p})},n.day))})})]})]}):e.jsx(ie,{}),N&&e.jsx(Ce,{tripData:t,handleClickCancle:Z,getTrip:d})]})},as=o.memo(ve);export{as as default};
