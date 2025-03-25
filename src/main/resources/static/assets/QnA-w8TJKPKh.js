import{ai as C,_ as Q,ar as T,au as B,at as U,bo as F,av as H,o as ee,bG as ae,bp as J,z as ne,bJ as te,aA as le,bF as se,as as oe,r as _,D as re,v as ie,w as ce,q as E,aH as de,bI as me,aS as xe,bm as ue,bw as fe,bn as X,aq as V,j as s,g as ve,u as pe,b as ge,R as W}from"./index-CclgMCKg.js";import{b as z}from"./index-efnQCHqG.js";import{R as be}from"./RightOutlined-DxWTf1i6.js";import{i as he}from"./motion-BtiDeT7c.js";import{g as ye}from"./collapse-BbEVqHco.js";var Y=C.forwardRef(function(a,e){var n=a.prefixCls,t=a.forceRender,r=a.className,c=a.style,d=a.children,i=a.isActive,x=a.role,p=a.classNames,$=a.styles,u=C.useState(i||t),g=Q(u,2),l=g[0],m=g[1];return C.useEffect(function(){(t||i)&&m(!0)},[t,i]),l?C.createElement("div",{ref:e,className:T("".concat(n,"-content"),B(B({},"".concat(n,"-content-active"),i),"".concat(n,"-content-inactive"),!i),r),style:c,role:x},C.createElement("div",{className:T("".concat(n,"-content-box"),p==null?void 0:p.body),style:$==null?void 0:$.body},d)):null});Y.displayName="PanelContent";var Ce=["showArrow","headerClass","isActive","onItemClick","forceRender","className","classNames","styles","prefixCls","collapsible","accordion","panelKey","extra","header","expandIcon","openMotion","destroyInactivePanel","children"],Z=C.forwardRef(function(a,e){var n=a.showArrow,t=n===void 0?!0:n,r=a.headerClass,c=a.isActive,d=a.onItemClick,i=a.forceRender,x=a.className,p=a.classNames,$=p===void 0?{}:p,u=a.styles,g=u===void 0?{}:u,l=a.prefixCls,m=a.collapsible,b=a.accordion,h=a.panelKey,v=a.extra,o=a.header,I=a.expandIcon,f=a.openMotion,S=a.destroyInactivePanel,N=a.children,j=U(a,Ce),y=m==="disabled",P=v!=null&&typeof v!="boolean",k=B(B(B({onClick:function(){d==null||d(h)},onKeyDown:function(M){(M.key==="Enter"||M.keyCode===F.ENTER||M.which===F.ENTER)&&(d==null||d(h))},role:b?"tab":"button"},"aria-expanded",c),"aria-disabled",y),"tabIndex",y?-1:0),R=typeof I=="function"?I(a):C.createElement("i",{className:"arrow"}),G=R&&C.createElement("div",H({className:"".concat(l,"-expand-icon")},["header","icon"].includes(m)?k:{}),R),w=T("".concat(l,"-item"),B(B({},"".concat(l,"-item-active"),c),"".concat(l,"-item-disabled"),y),x),A=T(r,"".concat(l,"-header"),B({},"".concat(l,"-collapsible-").concat(m),!!m),$.header),O=ee({className:A,style:g.header},["header","icon"].includes(m)?{}:k);return C.createElement("div",H({},j,{ref:e,className:w}),C.createElement("div",O,t&&G,C.createElement("span",H({className:"".concat(l,"-header-text")},m==="header"?k:{}),o),P&&C.createElement("div",{className:"".concat(l,"-extra")},v)),C.createElement(ae,H({visible:c,leavedClassName:"".concat(l,"-content-hidden")},f,{forceRender:i,removeOnLeave:S}),function(K,M){var L=K.className,D=K.style;return C.createElement(Y,{ref:M,prefixCls:l,className:L,classNames:$,style:D,styles:g,isActive:c,forceRender:i,role:b?"tabpanel":void 0},N)}))}),$e=["children","label","key","collapsible","onItemClick","destroyInactivePanel"],Ie=function(e,n){var t=n.prefixCls,r=n.accordion,c=n.collapsible,d=n.destroyInactivePanel,i=n.onItemClick,x=n.activeKey,p=n.openMotion,$=n.expandIcon;return e.map(function(u,g){var l=u.children,m=u.label,b=u.key,h=u.collapsible,v=u.onItemClick,o=u.destroyInactivePanel,I=U(u,$e),f=String(b??g),S=h??c,N=o??d,j=function(k){S!=="disabled"&&(i(k),v==null||v(k))},y=!1;return r?y=x[0]===f:y=x.indexOf(f)>-1,C.createElement(Z,H({},I,{prefixCls:t,key:f,panelKey:f,isActive:y,accordion:r,openMotion:p,expandIcon:$,header:m,collapsible:S,onItemClick:j,destroyInactivePanel:N}),l)})},Ne=function(e,n,t){if(!e)return null;var r=t.prefixCls,c=t.accordion,d=t.collapsible,i=t.destroyInactivePanel,x=t.onItemClick,p=t.activeKey,$=t.openMotion,u=t.expandIcon,g=e.key||String(n),l=e.props,m=l.header,b=l.headerClass,h=l.destroyInactivePanel,v=l.collapsible,o=l.onItemClick,I=!1;c?I=p[0]===g:I=p.indexOf(g)>-1;var f=v??d,S=function(y){f!=="disabled"&&(x(y),o==null||o(y))},N={key:g,panelKey:g,header:m,headerClass:b,isActive:I,prefixCls:r,destroyInactivePanel:h??i,openMotion:$,accordion:c,children:e.props.children,onItemClick:S,expandIcon:u,collapsible:f};return typeof e.type=="string"?e:(Object.keys(N).forEach(function(j){typeof N[j]>"u"&&delete N[j]}),C.cloneElement(e,N))};function Pe(a,e,n){return Array.isArray(a)?Ie(a,n):J(e).map(function(t,r){return Ne(t,r,n)})}function Se(a){var e=a;if(!Array.isArray(e)){var n=se(e);e=n==="number"||n==="string"?[e]:[]}return e.map(function(t){return String(t)})}var je=C.forwardRef(function(a,e){var n=a.prefixCls,t=n===void 0?"rc-collapse":n,r=a.destroyInactivePanel,c=r===void 0?!1:r,d=a.style,i=a.accordion,x=a.className,p=a.children,$=a.collapsible,u=a.openMotion,g=a.expandIcon,l=a.activeKey,m=a.defaultActiveKey,b=a.onChange,h=a.items,v=T(t,x),o=ne([],{value:l,onChange:function(P){return b==null?void 0:b(P)},defaultValue:m,postState:Se}),I=Q(o,2),f=I[0],S=I[1],N=function(P){return S(function(){if(i)return f[0]===P?[]:[P];var k=f.indexOf(P),R=k>-1;return R?f.filter(function(G){return G!==P}):[].concat(oe(f),[P])})};te(!p,"[rc-collapse] `children` will be removed in next major version. Please use `items` instead.");var j=Pe(h,p,{prefixCls:t,accordion:i,openMotion:u,expandIcon:g,collapsible:$,destroyInactivePanel:c,onItemClick:N,activeKey:f});return C.createElement("div",H({ref:e,className:v,style:d,role:i?"tablist":void 0},le(a,{aria:!0,data:!0})),j)});const q=Object.assign(je,{Panel:Z});q.Panel;const we=_.forwardRef((a,e)=>{const{getPrefixCls:n}=_.useContext(re),{prefixCls:t,className:r,showArrow:c=!0}=a,d=n("collapse",t),i=T({[`${d}-no-arrow`]:!c},r);return _.createElement(q.Panel,Object.assign({ref:e},a,{prefixCls:d,className:i}))}),ke=a=>{const{componentCls:e,contentBg:n,padding:t,headerBg:r,headerPadding:c,collapseHeaderPaddingSM:d,collapseHeaderPaddingLG:i,collapsePanelBorderRadius:x,lineWidth:p,lineType:$,colorBorder:u,colorText:g,colorTextHeading:l,colorTextDisabled:m,fontSizeLG:b,lineHeight:h,lineHeightLG:v,marginSM:o,paddingSM:I,paddingLG:f,paddingXS:S,motionDurationSlow:N,fontSizeIcon:j,contentPadding:y,fontHeight:P,fontHeightLG:k}=a,R=`${E(p)} ${$} ${u}`;return{[e]:Object.assign(Object.assign({},de(a)),{backgroundColor:r,border:R,borderRadius:x,"&-rtl":{direction:"rtl"},[`& > ${e}-item`]:{borderBottom:R,"&:first-child":{[`
            &,
            & > ${e}-header`]:{borderRadius:`${E(x)} ${E(x)} 0 0`}},"&:last-child":{[`
            &,
            & > ${e}-header`]:{borderRadius:`0 0 ${E(x)} ${E(x)}`}},[`> ${e}-header`]:Object.assign(Object.assign({position:"relative",display:"flex",flexWrap:"nowrap",alignItems:"flex-start",padding:c,color:l,lineHeight:h,cursor:"pointer",transition:`all ${N}, visibility 0s`},me(a)),{[`> ${e}-header-text`]:{flex:"auto"},[`${e}-expand-icon`]:{height:P,display:"flex",alignItems:"center",paddingInlineEnd:o},[`${e}-arrow`]:Object.assign(Object.assign({},xe()),{fontSize:j,transition:`transform ${N}`,svg:{transition:`transform ${N}`}}),[`${e}-header-text`]:{marginInlineEnd:"auto"}}),[`${e}-collapsible-header`]:{cursor:"default",[`${e}-header-text`]:{flex:"none",cursor:"pointer"}},[`${e}-collapsible-icon`]:{cursor:"unset",[`${e}-expand-icon`]:{cursor:"pointer"}}},[`${e}-content`]:{color:g,backgroundColor:n,borderTop:R,[`& > ${e}-content-box`]:{padding:y},"&-hidden":{display:"none"}},"&-small":{[`> ${e}-item`]:{[`> ${e}-header`]:{padding:d,paddingInlineStart:S,[`> ${e}-expand-icon`]:{marginInlineStart:a.calc(I).sub(S).equal()}},[`> ${e}-content > ${e}-content-box`]:{padding:I}}},"&-large":{[`> ${e}-item`]:{fontSize:b,lineHeight:v,[`> ${e}-header`]:{padding:i,paddingInlineStart:t,[`> ${e}-expand-icon`]:{height:k,marginInlineStart:a.calc(f).sub(t).equal()}},[`> ${e}-content > ${e}-content-box`]:{padding:f}}},[`${e}-item:last-child`]:{borderBottom:0,[`> ${e}-content`]:{borderRadius:`0 0 ${E(x)} ${E(x)}`}},[`& ${e}-item-disabled > ${e}-header`]:{"\n          &,\n          & > .arrow\n        ":{color:m,cursor:"not-allowed"}},[`&${e}-icon-position-end`]:{[`& > ${e}-item`]:{[`> ${e}-header`]:{[`${e}-expand-icon`]:{order:1,paddingInlineEnd:0,paddingInlineStart:o}}}}})}},Ae=a=>{const{componentCls:e}=a,n=`> ${e}-item > ${e}-header ${e}-arrow`;return{[`${e}-rtl`]:{[n]:{transform:"rotate(180deg)"}}}},Ee=a=>{const{componentCls:e,headerBg:n,paddingXXS:t,colorBorder:r}=a;return{[`${e}-borderless`]:{backgroundColor:n,border:0,[`> ${e}-item`]:{borderBottom:`1px solid ${r}`},[`
        > ${e}-item:last-child,
        > ${e}-item:last-child ${e}-header
      `]:{borderRadius:0},[`> ${e}-item:last-child`]:{borderBottom:0},[`> ${e}-item > ${e}-content`]:{backgroundColor:"transparent",borderTop:0},[`> ${e}-item > ${e}-content > ${e}-content-box`]:{paddingTop:t}}}},Re=a=>{const{componentCls:e,paddingSM:n}=a;return{[`${e}-ghost`]:{backgroundColor:"transparent",border:0,[`> ${e}-item`]:{borderBottom:0,[`> ${e}-content`]:{backgroundColor:"transparent",border:0,[`> ${e}-content-box`]:{paddingBlock:n}}}}}},Me=a=>({headerPadding:`${a.paddingSM}px ${a.padding}px`,headerBg:a.colorFillAlter,contentPadding:`${a.padding}px 16px`,contentBg:a.colorBgContainer}),Oe=ie("Collapse",a=>{const e=ce(a,{collapseHeaderPaddingSM:`${E(a.paddingXS)} ${E(a.paddingSM)}`,collapseHeaderPaddingLG:`${E(a.padding)} ${E(a.paddingLG)}`,collapsePanelBorderRadius:a.borderRadiusLG});return[ke(e),Ee(e),Re(e),Ae(e),ye(e)]},Me),_e=_.forwardRef((a,e)=>{const{getPrefixCls:n,direction:t,expandIcon:r,className:c,style:d}=ue("collapse"),{prefixCls:i,className:x,rootClassName:p,style:$,bordered:u=!0,ghost:g,size:l,expandIconPosition:m="start",children:b,expandIcon:h}=a,v=fe(w=>{var A;return(A=l??w)!==null&&A!==void 0?A:"middle"}),o=n("collapse",i),I=n(),[f,S,N]=Oe(o),j=_.useMemo(()=>m==="left"?"start":m==="right"?"end":m,[m]),y=h??r,P=_.useCallback(function(){let w=arguments.length>0&&arguments[0]!==void 0?arguments[0]:{};const A=typeof y=="function"?y(w):_.createElement(be,{rotate:w.isActive?t==="rtl"?-90:90:void 0,"aria-label":w.isActive?"expanded":"collapsed"});return X(A,()=>{var O;return{className:T((O=A==null?void 0:A.props)===null||O===void 0?void 0:O.className,`${o}-arrow`)}})},[y,o]),k=T(`${o}-icon-position-${j}`,{[`${o}-borderless`]:!u,[`${o}-rtl`]:t==="rtl",[`${o}-ghost`]:!!g,[`${o}-${v}`]:v!=="middle"},c,x,p,S,N),R=Object.assign(Object.assign({},he(I)),{motionAppear:!1,leavedClassName:`${o}-content-hidden`}),G=_.useMemo(()=>b?J(b).map((w,A)=>{var O,K;const M=w.props;if(M!=null&&M.disabled){const L=(O=w.key)!==null&&O!==void 0?O:String(A),D=Object.assign(Object.assign({},V(w.props,["disabled"])),{key:L,collapsible:(K=M.collapsible)!==null&&K!==void 0?K:"disabled"});return X(w,D)}return w}):null,[b]);return f(_.createElement(q,Object.assign({ref:e,openMotion:R},V(a,["rootClassName"]),{expandIcon:P,prefixCls:o,className:k,style:Object.assign(Object.assign({},d),$)}),G))}),Ke=Object.assign(_e,{Panel:we}),Be=[{key:"1",label:s.jsxs("p",{className:"text-lg font-semibold flex items-center gap-2 text-slate-700",children:[s.jsx("span",{className:"text-primary text-2xl",children:s.jsx(z,{})}),"숙소 예약 방법"]}),children:s.jsx("div",{className:"text-sm text-slate-600 py-5",children:"숙소를 예약하려면 원하는 날짜와 지역을 선택한 후 검색을 진행하세요. 검색 결과에서 원하는 숙소를 선택한 후 예약하기 버튼을 클릭하여 결제를 완료하면 됩니다."})},{key:"2",label:s.jsxs("p",{className:"text-lg font-semibold flex items-center gap-2 text-slate-700",children:[s.jsx("span",{className:"text-primary text-2xl",children:s.jsx(z,{})}),"예약 취소 및 변경 방법"]}),children:s.jsx("div",{className:"text-sm text-slate-600 py-5",children:"예약을 취소하거나 변경하려면 마이페이지에서 예약 내역을 확인한 후 취소 또는 변경 버튼을 클릭하세요. 일부 숙소는 취소 수수료가 발생할 수 있으니 유의하시기 바랍니다."})},{key:"3",label:s.jsxs("p",{className:"text-lg font-semibold flex items-center gap-2 text-slate-700",children:[s.jsx("span",{className:"text-primary text-2xl",children:s.jsx(z,{})}),"결제 방법"]}),children:s.jsx("div",{className:"text-sm text-slate-600 py-5",children:"신용카드, 체크카드, 간편결제(카카오페이, 네이버페이 등)를 통해 결제가 가능합니다. 결제 수단에 따라 할부 옵션이 제공될 수 있습니다."})},{key:"4",label:s.jsxs("p",{className:"text-lg font-semibold flex items-center gap-2 text-slate-700",children:[s.jsx("span",{className:"text-primary text-2xl",children:s.jsx(z,{})}),"체크인 및 체크아웃 시간"]}),children:s.jsx("div",{className:"text-sm text-slate-600 py-5",children:"일반적으로 체크인은 오후 3시 이후, 체크아웃은 오전 11시까지입니다. 숙소마다 시간이 다를 수 있으므로 예약 시 확인하시기 바랍니다."})},{key:"5",label:s.jsxs("p",{className:"text-lg font-semibold flex items-center gap-2 text-slate-700",children:[s.jsx("span",{className:"text-primary text-2xl",children:s.jsx(z,{})}),"반려동물 동반 가능 여부"]}),children:s.jsx("div",{className:"text-sm text-slate-600 py-5",children:"일부 숙소는 반려동물 동반이 가능하지만, 추가 요금이 부과될 수 있습니다. 예약 전 숙소의 반려동물 정책을 꼭 확인하세요."})},{key:"6",label:s.jsxs("p",{className:"text-lg font-semibold flex items-center gap-2 text-slate-700",children:[s.jsx("span",{className:"text-primary text-2xl",children:s.jsx(z,{})}),"글꼴이 왜 그런가요?"]}),children:s.jsx("div",{className:"text-sm text-slate-600 py-5",children:"일부 숙소는 반려동물 동반이 가능하지만, 추가 요금이 부과될 수 있습니다. 예약 전 숙소의 반려동물 정책을 꼭 확인하세요."})}],De=()=>{const e=ve("user").role[0],n=pe(),t=()=>{console.log("role",e),e===W.USER&&n("/"),e===W.BUSI&&n("/business")},r=c=>{console.log(c)};return s.jsxs("div",{className:"max-w-[768px] mx-auto",children:[s.jsx(ge,{title:"자주 묻는 질문",icon:"close",onClick:()=>t()}),s.jsx("section",{className:"w-full  px-4",children:s.jsx(Ke,{defaultActiveKey:["1"],onChange:r,expandIconPosition:"end",items:Be,bordered:!1,style:{background:"white",borderRadius:0,width:"100%"},className:"w-full [&_.ant-collapse-content-box]:bg-slate-100"})})]})};export{De as default};
