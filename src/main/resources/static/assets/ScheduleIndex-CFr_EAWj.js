import{r,c as ee,R as te,J as le,u as se,j as e,I as ie,B as Q,f as L,g as ce,K as de,L as xe}from"./index-BldMvkj-.js";import{T as pe,R as me}from"./TitleHeader-h4a5ZHCC.js";import{A as W}from"./index-C0FmFyOa.js";import{u as ue,a as X,D as he,c as ge,b as fe,v as ye,S as Y,K as je,P as be}from"./ScheduleDay-DmBpO-4H.js";import{t as we}from"./tripAtom-BglzYsFw.js";import{a as ke}from"./index-wJUF33C7.js";import{d as f}from"./dayjs.min-Euf2t-ZC.js";/* empty css               */import"./swiper-react-BGM9AzWg.js";import{c as Ie,D as ve}from"./index-DDl8LP5A.js";import{n as ne,D as Ce}from"./emotion-styled.browser.esm-CgeflzAN.js";import{e as Z}from"./EditTripAtom-BpwKFiFR.js";import{C as oe}from"./index-CLRk82Bw.js";import{F as g}from"./index-Dqm2EJ75.js";import"./index-Bo3kiOEp.js";import"./index-CF8iy-oi.js";import"./index-ChVfxpTv.js";import"./match-Cr-WHery.js";import"./index-C5B1RAqx.js";import"./pic-Jp7D6aQ0.js";import"./CenterModal-8n8fSPlT.js";import"./index-Dkwrf7my.js";import"./KeyCode-DNlgD2sM.js";import"./index-BWTVzI-L.js";import"./index-B5HDwGmv.js";import"./Portal-w-6SWf8e.js";import"./useId-DZuEBq-G.js";import"./useZIndex-BlWCPQk6.js";import"./zoom-CY_GuPzI.js";import"./Keyframes-4MZitI5l.js";import"./PurePanel-DrBmxA6O.js";import"./Overflow-C9jBxujf.js";import"./useIcons-DWBTnG39.js";import"./CheckOutlined-B8loTIFt.js";import"./CloseOutlined-CG4VXA4p.js";import"./useLocale-pc8lhoMq.js";import"./EllipsisOutlined-CFg6-N57.js";import"./collapse-BbEVqHco.js";import"./index-BL1VZDQG.js";import"./Checkbox-ICrtv2jw.js";import"./index-CTXNrfD5.js";import"./ExclamationCircleFilled-BgEVQkYu.js";const{RangePicker:D}=ve;f.extend(Ie);const Ne=ne(oe)`
  && {
    .ant-checkbox {
      order: 2;
      transform: scale(2);
      margin-left: 8px;
      margin-right: 0;

      // 체크박스를 동그라미로 만들기
      .ant-checkbox-inner {
        border-radius: 50%; // 동그라미 모양으로 변경
        &::after {
          inset-inline-start: 25%; // 체크 마크의 시작 위치를 조정
          top: 45%; // 상단 위치 미세 조정
          width: 5.714286px; // 체크 마크 크기 조정
          height: 9.142857px; // 체크 마크 크기 조정
        }
      }
    }
    .ant-checkbox-wrapper {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      width: 100%;
    }
    .ant-checkbox + span {
      order: 1;
      padding-left: 0;
      padding-right: 8px;
    }
  }
`,Se=({tripData:t,handleClickCancle:y,getTrip:T})=>{var w,c;const[l]=g.useForm(),[j]=ee(),b=parseInt(j.get("tripId")),[K,E]=te(Z),s=le(Z),p=se(),_=()=>{y()},P=n=>{n.stopPropagation()},N=(n,a)=>{console.log("선택된 날짜:",n),console.log("포맷된 날짜:",a);const m=a==null?void 0:a.map(S=>S+":00");setDates(m)},$=n=>n&&n<f().endOf("day");ne(D)`
    .ant-picker-input input {
      color: "#334155" !important;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 0 10px;
      width: 130px;
    }
  `;const h=async n=>{try{const a=await L.patch("/api/trip",n);console.log("여행 수정",a.data);const m=a.data;m&&(T(),y()),m.code==="200 성공"&&s()}catch(a){console.log("여행 수정",a)}},U=n=>{var A;console.log(n);const{title:a,rangePicker:m,nowUser:S}=n,k=t!=null&&t.nowUser?t.nowUser.map(x=>x.userId).filter(x=>{var u;return!((u=n.nowUser)!=null&&u.includes(x))}):[];(A=n.nowUser)!=null&&A.filter(x=>!k.includes(x));const I=m.map(x=>x.format("YYYY-MM-DD"));console.log(I);const i={title:a,trip_id:b,start_at:I[0],end_at:I[1],del_user_list:k,ins_location_list:[...t==null?void 0:t.tripLocationList],del_location_list:[]};h(i)},F=()=>{console.log("지역 선택"),E({title:t.title,startDate:t.startAt,endDate:t.endAt,nowUser:t.tripUserIdList,tripLocationList:t.tripLocationList,from:`/schedule/index?tripId=${b}`,isEdit:!0}),p("/search/location",{state:{tripLocationList:t.tripLocationList,from:`/schedule/index?tripId=${b}`}})};return e.jsx("div",{className:`fixed top-0 left-[50%] translate-x-[-50%] z-10\r
            max-w-3xl w-full mx-auto h-screen\r
            flex items-center justify-center\r
            bg-[rgba(0,0,0,0.5)]\r
            `,onClick:()=>{_()},children:e.jsxs("div",{className:`bg-white \r
                rounded-2xl px-[60px] pt-[30px] pb-[10px]\r
                flex flex-col items-center justify-center\r
                gap-[20px]\r
                `,onClick:P,children:[e.jsxs("div",{className:"flex gap-[20px]",children:[e.jsx("button",{type:"button",onClick:F,children:"지역 선택"}),e.jsx("ul",{children:(w=t==null?void 0:t.tripLocationList)==null?void 0:w.map((n,a)=>e.jsx("li",{children:n},a))})]}),e.jsxs(g,{form:l,onFinish:U,requiredMark:!1,style:{width:"100%"},children:[e.jsx(g.Item,{name:"title",label:"제목",rules:[{required:!0,message:"제목을 입력해주세요."}],initialValue:t.title,children:e.jsx(ie,{})}),e.jsx(g.Item,{name:"rangePicker",label:"여행일자",rules:[{required:!0,message:"여행일자를 입력해주세요."}],initialValue:t!=null&&t.startAt&&(t!=null&&t.endAt)?[f(t.startAt),f(t.endAt)]:void 0,children:e.jsx(D,{placeholder:["시작일 ","종료일"],disabledDate:$,variant:"borderless",onChange:N,separator:"~"})}),e.jsx(g.Item,{name:"nowUser",label:"참여자",initialValue:(t==null?void 0:t.tripUserIdList)||[],children:e.jsx(oe.Group,{children:((c=t==null?void 0:t.tripUserIdList)==null?void 0:c.map((n,a)=>e.jsx(Ne,{value:n,size:"large",children:n},a)))||null})}),e.jsx(g.Item,{style:{width:"100%"},children:e.jsxs("div",{style:{display:"flex",gap:"10px"},children:[e.jsx(Q,{color:"default",variant:"filled",htmlType:"button",onClick:y,style:{width:"50%"},children:"취소"}),e.jsx(Q,{type:"primary",htmlType:"submit",style:{width:"50%"},children:"수정"})]})})]})]})})},Ae=r.memo(Se),Le={day:1,weather:"",schedules:[]},Te=()=>{var z;ce("accessToken");const[t,y]=te(we);r.useEffect(()=>{console.log("trip",t)},[t]);const[T]=ee(),l=parseInt(T.get("tripId"));r.useEffect(()=>{y({...t,nowTripId:l})},[]);const j=se(),b=()=>{j(-1)},K=()=>{j(`/schedule/calculation?tripId=${l}`)},E=()=>{j(`/scheduleboard/schedulePost?tripId=${l}`)},[s,p]=r.useState({}),[_,P]=r.useState(!1),[N,$]=r.useState(s.title),[h,U]=r.useState(""),[F,w]=r.useState(!1),[c,n]=r.useState(!1);r.useEffect(()=>{i()},[]),r.useState(!1);const[a,m]=r.useState({tripId:null,scheduleId:null,originDay:null,destDay:null,originSeq:null,destSeq:null});r.useEffect(()=>{console.log("여행 데이터",s)},[s]),r.useEffect(()=>{console.log("링크",h)},[h]),r.useEffect(()=>{console.log("title",N)},[N]);const S=r.useCallback(async()=>{try{const o=await L.get(`/api/trip/add-link?trip_id=${l}`);console.log(o.data),U(o.data.data)}catch(o){console.log("초대코드",o)}},[]),k=r.useCallback(async()=>{try{await navigator.clipboard.writeText(h),console.log("복사 성공")}catch(o){console.error("복사 실패:",o)}},[]),I=[{label:e.jsxs("div",{onClick:()=>k(),className:"flex flex-col gap-[10px] items-center justify-center",children:[e.jsx("p",{className:"bg-slate-100 px-[15px] py-[10px] rounded-lg text-slate-600",children:h}),e.jsxs("p",{className:"flex items-center gap-1 border-b border-slate-300",children:[e.jsx("i",{className:"text-slate-500",children:e.jsx(ke,{})}),e.jsx("span",{className:"text-slate-500",children:"초대코드 복사하기"})]})]}),key:"0"}],i=r.useCallback(async()=>{try{const o=await L.get(`/api/trip?trip_id=${l}&signed=true`);console.log("여행확인하기",o.data);const d=o.data.data;p(d),d&&P(!0)}catch(o){console.log(o)}},[]),A=r.useCallback(async o=>{try{const d=await L.patch("/api/schedule",o);console.log("일정 순서 변경 성공:",d.data)}catch(d){console.log("일정 순서 변경 실패:",d),i()}},[]),x=()=>{w(!1)};r.useEffect(()=>{i(),s&&$(s.title)},[]);const u=s.days,re=ue(X(be),X(je)),ae=async o=>{const{active:d,over:M}=o;if(!M)return;const[v,q]=d.id.split("-").map(Number),[R,B]=M.id.split("-").map(Number);if(v===R&&q===B)return;const O=structuredClone(s),G=O.days.find(C=>C.day===v),H=O.days.find(C=>C.day===R);if(!G||!H)return;const[J]=G.schedules.splice(q,1);H.schedules.splice(B,0,J),p(O);try{await A({tripId:l,scheduleId:J.scheduleMemoId,originDay:v,destDay:R,originSeq:q+1,destSeq:B+1})}catch(C){console.error("Failed to update schedule order:",C),i()}};r.useEffect(()=>{a.tripId&&console.log("드래그 정보 상태 업데이트:",a)},[a]);const V=f(s.endAt).diff(f(s.startAt),"day");return e.jsxs("div",{children:[_?e.jsxs(e.Fragment,{children:[e.jsx(pe,{icon:"back",onClick:b,rightContent:e.jsx(me,{icon1:!1,icon2:!0,icon3Click:E,icon3:!0,icon4:!0})}),e.jsxs("div",{className:"flex flex-col gap-[30px] py-[30px]",children:[e.jsxs("div",{className:"mt-[60px] flex flex-col gap-[10px] px-[32px]",children:[e.jsxs("div",{className:"flex items-center justify-between",children:[e.jsxs("p",{className:"text-[18px] text-slate-700 flex gap-[10px]",children:[e.jsxs("span",{children:[s.startAt," ~ ",s.endAt]}),e.jsx("span",{children:s.startAt&&s.endAt&&`(${V}박 ${V+1}일)`})]}),e.jsx("button",{type:"button",onClick:()=>w(!0),children:e.jsx(de,{className:"text-[24px] text-slate-300 bg-white"})})]}),e.jsx("h2",{className:"text-[36px] text-slate-700 font-bold",children:s.title}),e.jsx("div",{className:"flex items-center gap-3 mt-5"})]}),e.jsxs("div",{className:"flex items-center justify-between gap-[10px] px-[32px]",children:[e.jsxs("div",{className:"flex items-center gap-[10px]",children:[e.jsx(Ce,{menu:{items:I},trigger:["click"],overlayStyle:{marginTop:"10px"},children:e.jsxs("button",{type:"button",className:`flex items-center gap-[10px] \r
                  px-[15px] py-[10px] rounded-3xl\r
                  text-white bg-primary\r
                  hover:bg-primary/80 transition-all duration-300`,onClick:async()=>{await S(),k()},children:[e.jsx(W,{}),"초대 코드"]})}),e.jsxs("button",{type:"button",className:`flex items-center gap-[10px] \r
                px-[15px] py-[10px] rounded-3xl\r
                text-slate-500 bg-slate-100\r
                hover:bg-slate-200/80 transition-all duration-300`,onClick:K,children:[e.jsx(W,{className:"text-slate-300"}),"가계부"]})]}),e.jsxs("div",{className:"flex items-center gap-[10px]",children:[e.jsx("button",{type:"button",className:`flex items-center gap-[10px] \r
                  px-[15px] py-[10px] rounded-3xl\r
                  text-slate-500 bg-slate-100\r
                  hover:bg-slate-200/80 transition-all duration-300`,children:"참여 인원"}),e.jsx("button",{type:"button",className:`flex items-center gap-[10px] 
                    px-[15px] py-[10px] rounded-3xl transition-all duration-300
                    ${c?"text-white bg-primary hover:bg-primary/80":"text-slate-500 bg-slate-100 hover:bg-slate-200/80"}`,onClick:()=>{c?(n(!1),i()):n(!0)},children:c?"완료":"일정 편집"})]})]}),e.jsx("div",{className:"flex flex-col gap-[50px]",children:c?e.jsx(he,{sensors:re,collisionDetection:ge,onDragEnd:ae,children:(z=s.days)==null?void 0:z.map((o,d)=>e.jsx(fe,{items:o.schedules.map((M,v)=>`${o.day}-${v}`),strategy:ye,children:e.jsx(Y,{newTrip:!0,data:o,startAt:s==null?void 0:s.startAt,tripId:l,getTrip:i,setTripData:p,isDragging:c,setIsDragging:n})},o.day))}):e.jsx(e.Fragment,{children:u===null?e.jsx(Y,{newTrip:!0,data:Le,startAt:s==null?void 0:s.startAt,tripId:l,getTrip:i,setTripData:p,isDragging:c,setIsDragging:n}):u==null?void 0:u.map((o,d)=>e.jsx(Y,{newTrip:!0,data:o,startAt:s==null?void 0:s.startAt,tripId:l,getTrip:i,setTripData:p,isDragging:c,setIsDragging:n},o.day))})})]})]}):e.jsx(xe,{}),F&&e.jsx(Ae,{tripData:s,handleClickCancle:x,getTrip:i})]})},jt=r.memo(Te);export{jt as default};
