import{r as t,aw as Se,bo as le,aL as Gt,bQ as qt,ai as ee,bD as Yt,at as re,_ as A,ar as de,av as $,au as x,cW as Wn,cR as Xt,o as k,as as ye,bS as Hn,z as Ye,bJ as Jt,bE as jn,aq as bt,cX as Bn,cY as Gn,bW as qn,bV as Yn,bp as Xn,bG as Jn,bF as Zn,bA as Qn,ax as er}from"./index-CclgMCKg.js";import{T as Zt}from"./index-Dx4kxOlF.js";import{F as $e}from"./Overflow-b40CqTdl.js";var tr=le.ESC,nr=le.TAB;function rr(e){var r=e.visible,a=e.triggerRef,n=e.onVisibleChange,i=e.autoFocus,o=e.overlayRef,l=t.useRef(!1),u=function(){if(r){var s,d;(s=a.current)===null||s===void 0||(d=s.focus)===null||d===void 0||d.call(s),n==null||n(!1)}},v=function(){var s;return(s=o.current)!==null&&s!==void 0&&s.focus?(o.current.focus(),l.current=!0,!0):!1},c=function(s){switch(s.keyCode){case tr:u();break;case nr:{var d=!1;l.current||(d=v()),d?s.preventDefault():u();break}}};t.useEffect(function(){return r?(window.addEventListener("keydown",c),i&&Se(v,3),function(){window.removeEventListener("keydown",c),l.current=!1}):function(){l.current=!1}},[r])}var ar=t.forwardRef(function(e,r){var a=e.overlay,n=e.arrow,i=e.prefixCls,o=t.useMemo(function(){var u;return typeof a=="function"?u=a():u=a,u},[a]),l=Gt(r,qt(o));return ee.createElement(ee.Fragment,null,n&&ee.createElement("div",{className:"".concat(i,"-arrow")}),ee.cloneElement(o,{ref:Yt(o)?l:void 0}))}),Re={adjustX:1,adjustY:1},Ie=[0,0],ir={topLeft:{points:["bl","tl"],overflow:Re,offset:[0,-4],targetOffset:Ie},top:{points:["bc","tc"],overflow:Re,offset:[0,-4],targetOffset:Ie},topRight:{points:["br","tr"],overflow:Re,offset:[0,-4],targetOffset:Ie},bottomLeft:{points:["tl","bl"],overflow:Re,offset:[0,4],targetOffset:Ie},bottom:{points:["tc","bc"],overflow:Re,offset:[0,4],targetOffset:Ie},bottomRight:{points:["tr","br"],overflow:Re,offset:[0,4],targetOffset:Ie}},or=["arrow","prefixCls","transitionName","animation","align","placement","placements","getPopupContainer","showAction","hideAction","overlayClassName","overlayStyle","visible","trigger","autoFocus","overlay","children","onVisibleChange"];function lr(e,r){var a,n=e.arrow,i=n===void 0?!1:n,o=e.prefixCls,l=o===void 0?"rc-dropdown":o,u=e.transitionName,v=e.animation,c=e.align,h=e.placement,s=h===void 0?"bottomLeft":h,d=e.placements,M=d===void 0?ir:d,b=e.getPopupContainer,I=e.showAction,m=e.hideAction,E=e.overlayClassName,P=e.overlayStyle,p=e.visible,g=e.trigger,C=g===void 0?["hover"]:g,f=e.autoFocus,D=e.overlay,y=e.children,L=e.onVisibleChange,_=re(e,or),K=ee.useState(),T=A(K,2),F=T[0],G=T[1],V="visible"in e?p:F,q=ee.useRef(null),z=ee.useRef(null),B=ee.useRef(null);ee.useImperativeHandle(r,function(){return q.current});var H=function(O){G(O),L==null||L(O)};rr({visible:V,triggerRef:B,onVisibleChange:H,autoFocus:f,overlayRef:z});var Y=function(O){var N=e.onOverlayClick;G(!1),N&&N(O)},te=function(){return ee.createElement(ar,{ref:z,overlay:D,prefixCls:l,arrow:i})},ce=function(){return typeof D=="function"?te:te()},ve=function(){var O=e.minOverlayWidthMatchTrigger,N=e.alignPoint;return"minOverlayWidthMatchTrigger"in e?O:!N},U=function(){var O=e.openClassName;return O!==void 0?O:"".concat(l,"-open")},j=ee.cloneElement(y,{className:de((a=y.props)===null||a===void 0?void 0:a.className,V&&U()),ref:Yt(y)?Gt(B,qt(y)):void 0}),ue=m;return!ue&&C.indexOf("contextMenu")!==-1&&(ue=["click"]),ee.createElement(Zt,$({builtinPlacements:M},_,{prefixCls:l,ref:q,popupClassName:de(E,x({},"".concat(l,"-show-arrow"),i)),popupStyle:P,action:C,showAction:I,hideAction:ue,popupPlacement:s,popupAlign:c,popupTransitionName:u,popupAnimation:v,popupVisible:V,stretch:ve()?"minWidth":"",popup:ce(),onPopupVisibleChange:H,onPopupClick:Y,getPopupContainer:b}),j)}const qr=ee.forwardRef(lr);var Qt=t.createContext(null);function en(e,r){return e===void 0?null:"".concat(e,"-").concat(r)}function tn(e){var r=t.useContext(Qt);return en(r,e)}var ur=["children","locked"],oe=t.createContext(null);function sr(e,r){var a=k({},e);return Object.keys(r).forEach(function(n){var i=r[n];i!==void 0&&(a[n]=i)}),a}function De(e){var r=e.children,a=e.locked,n=re(e,ur),i=t.useContext(oe),o=Wn(function(){return sr(i,n)},[i,n],function(l,u){return!a&&(l[0]!==u[0]||!Xt(l[1],u[1],!0))});return t.createElement(oe.Provider,{value:o},r)}var cr=[],nn=t.createContext(null);function Qe(){return t.useContext(nn)}var rn=t.createContext(cr);function Le(e){var r=t.useContext(rn);return t.useMemo(function(){return e!==void 0?[].concat(ye(r),[e]):r},[r,e])}var an=t.createContext(null),Ct=t.createContext({});function zt(e){var r=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;if(Hn(e)){var a=e.nodeName.toLowerCase(),n=["input","select","textarea","button"].includes(a)||e.isContentEditable||a==="a"&&!!e.getAttribute("href"),i=e.getAttribute("tabindex"),o=Number(i),l=null;return i&&!Number.isNaN(o)?l=o:n&&l===null&&(l=0),n&&e.disabled&&(l=null),l!==null&&(l>=0||r&&l<0)}return!1}function vr(e){var r=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1,a=ye(e.querySelectorAll("*")).filter(function(n){return zt(n,r)});return zt(e,r)&&a.unshift(e),a}var ft=le.LEFT,dt=le.RIGHT,mt=le.UP,Xe=le.DOWN,Je=le.ENTER,on=le.ESC,_e=le.HOME,Oe=le.END,Ut=[mt,Xe,ft,dt];function fr(e,r,a,n){var i,o="prev",l="next",u="children",v="parent";if(e==="inline"&&n===Je)return{inlineTrigger:!0};var c=x(x({},mt,o),Xe,l),h=x(x(x(x({},ft,a?l:o),dt,a?o:l),Xe,u),Je,u),s=x(x(x(x(x(x({},mt,o),Xe,l),Je,u),on,v),ft,a?u:v),dt,a?v:u),d={inline:c,horizontal:h,vertical:s,inlineSub:c,horizontalSub:s,verticalSub:s},M=(i=d["".concat(e).concat(r?"":"Sub")])===null||i===void 0?void 0:i[n];switch(M){case o:return{offset:-1,sibling:!0};case l:return{offset:1,sibling:!0};case v:return{offset:-1,sibling:!1};case u:return{offset:1,sibling:!1};default:return null}}function dr(e){for(var r=e;r;){if(r.getAttribute("data-menu-list"))return r;r=r.parentElement}return null}function mr(e,r){for(var a=e||document.activeElement;a;){if(r.has(a))return a;a=a.parentElement}return null}function yt(e,r){var a=vr(e,!0);return a.filter(function(n){return r.has(n)})}function Wt(e,r,a){var n=arguments.length>3&&arguments[3]!==void 0?arguments[3]:1;if(!e)return null;var i=yt(e,r),o=i.length,l=i.findIndex(function(u){return a===u});return n<0?l===-1?l=o-1:l-=1:n>0&&(l+=1),l=(l+o)%o,i[l]}var pt=function(r,a){var n=new Set,i=new Map,o=new Map;return r.forEach(function(l){var u=document.querySelector("[data-menu-id='".concat(en(a,l),"']"));u&&(n.add(u),o.set(u,l),i.set(l,u))}),{elements:n,key2element:i,element2key:o}};function pr(e,r,a,n,i,o,l,u,v,c){var h=t.useRef(),s=t.useRef();s.current=r;var d=function(){Se.cancel(h.current)};return t.useEffect(function(){return function(){d()}},[]),function(M){var b=M.which;if([].concat(Ut,[Je,on,_e,Oe]).includes(b)){var I=o(),m=pt(I,n),E=m,P=E.elements,p=E.key2element,g=E.element2key,C=p.get(r),f=mr(C,P),D=g.get(f),y=fr(e,l(D,!0).length===1,a,b);if(!y&&b!==_e&&b!==Oe)return;(Ut.includes(b)||[_e,Oe].includes(b))&&M.preventDefault();var L=function(z){if(z){var B=z,H=z.querySelector("a");H!=null&&H.getAttribute("href")&&(B=H);var Y=g.get(z);u(Y),d(),h.current=Se(function(){s.current===Y&&B.focus()})}};if([_e,Oe].includes(b)||y.sibling||!f){var _;!f||e==="inline"?_=i.current:_=dr(f);var K,T=yt(_,P);b===_e?K=T[0]:b===Oe?K=T[T.length-1]:K=Wt(_,P,f,y.offset),L(K)}else if(y.inlineTrigger)v(D);else if(y.offset>0)v(D,!0),d(),h.current=Se(function(){m=pt(I,n);var q=f.getAttribute("aria-controls"),z=document.getElementById(q),B=Wt(z,m.elements);L(B)},5);else if(y.offset<0){var F=l(D,!0),G=F[F.length-2],V=p.get(G);v(G,!1),L(V)}}c==null||c(M)}}function gr(e){Promise.resolve().then(e)}var Mt="__RC_UTIL_PATH_SPLIT__",Ht=function(r){return r.join(Mt)},hr=function(r){return r.split(Mt)},gt="rc-menu-more";function br(){var e=t.useState({}),r=A(e,2),a=r[1],n=t.useRef(new Map),i=t.useRef(new Map),o=t.useState([]),l=A(o,2),u=l[0],v=l[1],c=t.useRef(0),h=t.useRef(!1),s=function(){h.current||a({})},d=t.useCallback(function(p,g){var C=Ht(g);i.current.set(C,p),n.current.set(p,C),c.current+=1;var f=c.current;gr(function(){f===c.current&&s()})},[]),M=t.useCallback(function(p,g){var C=Ht(g);i.current.delete(C),n.current.delete(p)},[]),b=t.useCallback(function(p){v(p)},[]),I=t.useCallback(function(p,g){var C=n.current.get(p)||"",f=hr(C);return g&&u.includes(f[0])&&f.unshift(gt),f},[u]),m=t.useCallback(function(p,g){return p.filter(function(C){return C!==void 0}).some(function(C){var f=I(C,!0);return f.includes(g)})},[I]),E=function(){var g=ye(n.current.keys());return u.length&&g.push(gt),g},P=t.useCallback(function(p){var g="".concat(n.current.get(p)).concat(Mt),C=new Set;return ye(i.current.keys()).forEach(function(f){f.startsWith(g)&&C.add(i.current.get(f))}),C},[]);return t.useEffect(function(){return function(){h.current=!0}},[]),{registerPath:d,unregisterPath:M,refreshOverflowKeys:b,isSubPathKey:m,getKeyPath:I,getKeys:E,getSubPathKeys:P}}function Ae(e){var r=t.useRef(e);r.current=e;var a=t.useCallback(function(){for(var n,i=arguments.length,o=new Array(i),l=0;l<i;l++)o[l]=arguments[l];return(n=r.current)===null||n===void 0?void 0:n.call.apply(n,[r].concat(o))},[]);return e?a:void 0}var Cr=Math.random().toFixed(5).toString().slice(2),jt=0;function yr(e){var r=Ye(e,{value:e}),a=A(r,2),n=a[0],i=a[1];return t.useEffect(function(){jt+=1;var o="".concat(Cr,"-").concat(jt);i("rc-menu-uuid-".concat(o))},[]),n}function ln(e,r,a,n){var i=t.useContext(oe),o=i.activeKey,l=i.onActive,u=i.onInactive,v={active:o===e};return r||(v.onMouseEnter=function(c){a==null||a({key:e,domEvent:c}),l(e)},v.onMouseLeave=function(c){n==null||n({key:e,domEvent:c}),u(e)}),v}function un(e){var r=t.useContext(oe),a=r.mode,n=r.rtl,i=r.inlineIndent;if(a!=="inline")return null;var o=e;return n?{paddingRight:o*i}:{paddingLeft:o*i}}function sn(e){var r=e.icon,a=e.props,n=e.children,i;return r===null||r===!1?null:(typeof r=="function"?i=t.createElement(r,k({},a)):typeof r!="boolean"&&(i=r),i||n||null)}var Mr=["item"];function Ze(e){var r=e.item,a=re(e,Mr);return Object.defineProperty(a,"item",{get:function(){return Jt(!1,"`info.item` is deprecated since we will move to function component that not provides React Node instance in future."),r}}),a}var Rr=["title","attribute","elementRef"],Ir=["style","className","eventKey","warnKey","disabled","itemIcon","children","role","onMouseEnter","onMouseLeave","onClick","onKeyDown","onFocus"],Sr=["active"],xr=function(e){Bn(a,e);var r=Gn(a);function a(){return qn(this,a),r.apply(this,arguments)}return Yn(a,[{key:"render",value:function(){var i=this.props,o=i.title,l=i.attribute,u=i.elementRef,v=re(i,Rr),c=bt(v,["eventKey","popupClassName","popupOffset","onTitleClick"]);return Jt(!l,"`attribute` of Menu.Item is deprecated. Please pass attribute directly."),t.createElement($e.Item,$({},l,{title:typeof o=="string"?o:void 0},c,{ref:u}))}}]),a}(t.Component),wr=t.forwardRef(function(e,r){var a=e.style,n=e.className,i=e.eventKey;e.warnKey;var o=e.disabled,l=e.itemIcon,u=e.children,v=e.role,c=e.onMouseEnter,h=e.onMouseLeave,s=e.onClick,d=e.onKeyDown,M=e.onFocus,b=re(e,Ir),I=tn(i),m=t.useContext(oe),E=m.prefixCls,P=m.onItemClick,p=m.disabled,g=m.overflowDisabled,C=m.itemIcon,f=m.selectedKeys,D=m.onActive,y=t.useContext(Ct),L=y._internalRenderMenuItem,_="".concat(E,"-item"),K=t.useRef(),T=t.useRef(),F=p||o,G=jn(r,T),V=Le(i),q=function(N){return{key:i,keyPath:ye(V).reverse(),item:K.current,domEvent:N}},z=l||C,B=ln(i,F,c,h),H=B.active,Y=re(B,Sr),te=f.includes(i),ce=un(V.length),ve=function(N){if(!F){var ae=q(N);s==null||s(Ze(ae)),P(ae)}},U=function(N){if(d==null||d(N),N.which===le.ENTER){var ae=q(N);s==null||s(Ze(ae)),P(ae)}},j=function(N){D(i),M==null||M(N)},ue={};e.role==="option"&&(ue["aria-selected"]=te);var X=t.createElement(xr,$({ref:K,elementRef:G,role:v===null?"none":v||"menuitem",tabIndex:o?null:-1,"data-menu-id":g&&I?null:I},bt(b,["extra"]),Y,ue,{component:"li","aria-disabled":o,style:k(k({},ce),a),className:de(_,x(x(x({},"".concat(_,"-active"),H),"".concat(_,"-selected"),te),"".concat(_,"-disabled"),F),n),onClick:ve,onKeyDown:U,onFocus:j}),u,t.createElement(sn,{props:k(k({},e),{},{isSelected:te}),icon:z}));return L&&(X=L(X,e,{selected:te})),X});function Er(e,r){var a=e.eventKey,n=Qe(),i=Le(a);return t.useEffect(function(){if(n)return n.registerPath(a,i),function(){n.unregisterPath(a,i)}},[i]),n?null:t.createElement(wr,$({},e,{ref:r}))}const Rt=t.forwardRef(Er);var Pr=["className","children"],Nr=function(r,a){var n=r.className,i=r.children,o=re(r,Pr),l=t.useContext(oe),u=l.prefixCls,v=l.mode,c=l.rtl;return t.createElement("ul",$({className:de(u,c&&"".concat(u,"-rtl"),"".concat(u,"-sub"),"".concat(u,"-").concat(v==="inline"?"inline":"vertical"),n),role:"menu"},o,{"data-menu-list":!0,ref:a}),i)},It=t.forwardRef(Nr);It.displayName="SubMenuList";function St(e,r){return Xn(e).map(function(a,n){if(t.isValidElement(a)){var i,o,l=a.key,u=(i=(o=a.props)===null||o===void 0?void 0:o.eventKey)!==null&&i!==void 0?i:l,v=u==null;v&&(u="tmp_key-".concat([].concat(ye(r),[n]).join("-")));var c={key:u,eventKey:u};return t.cloneElement(a,c)}return a})}var W={adjustX:1,adjustY:1},Kr={topLeft:{points:["bl","tl"],overflow:W},topRight:{points:["br","tr"],overflow:W},bottomLeft:{points:["tl","bl"],overflow:W},bottomRight:{points:["tr","br"],overflow:W},leftTop:{points:["tr","tl"],overflow:W},leftBottom:{points:["br","bl"],overflow:W},rightTop:{points:["tl","tr"],overflow:W},rightBottom:{points:["bl","br"],overflow:W}},kr={topLeft:{points:["bl","tl"],overflow:W},topRight:{points:["br","tr"],overflow:W},bottomLeft:{points:["tl","bl"],overflow:W},bottomRight:{points:["tr","br"],overflow:W},rightTop:{points:["tr","tl"],overflow:W},rightBottom:{points:["br","bl"],overflow:W},leftTop:{points:["tl","tr"],overflow:W},leftBottom:{points:["bl","br"],overflow:W}};function cn(e,r,a){if(r)return r;if(a)return a[e]||a.other}var _r={horizontal:"bottomLeft",vertical:"rightTop","vertical-left":"rightTop","vertical-right":"leftTop"};function Or(e){var r=e.prefixCls,a=e.visible,n=e.children,i=e.popup,o=e.popupStyle,l=e.popupClassName,u=e.popupOffset,v=e.disabled,c=e.mode,h=e.onVisibleChange,s=t.useContext(oe),d=s.getPopupContainer,M=s.rtl,b=s.subMenuOpenDelay,I=s.subMenuCloseDelay,m=s.builtinPlacements,E=s.triggerSubMenuAction,P=s.forceSubMenuRender,p=s.rootClassName,g=s.motion,C=s.defaultMotions,f=t.useState(!1),D=A(f,2),y=D[0],L=D[1],_=M?k(k({},kr),m):k(k({},Kr),m),K=_r[c],T=cn(c,g,C),F=t.useRef(T);c!=="inline"&&(F.current=T);var G=k(k({},F.current),{},{leavedClassName:"".concat(r,"-hidden"),removeOnLeave:!1,motionAppear:!0}),V=t.useRef();return t.useEffect(function(){return V.current=Se(function(){L(a)}),function(){Se.cancel(V.current)}},[a]),t.createElement(Zt,{prefixCls:r,popupClassName:de("".concat(r,"-popup"),x({},"".concat(r,"-rtl"),M),l,p),stretch:c==="horizontal"?"minWidth":null,getPopupContainer:d,builtinPlacements:_,popupPlacement:K,popupVisible:y,popup:i,popupStyle:o,popupAlign:u&&{offset:u},action:v?[]:[E],mouseEnterDelay:b,mouseLeaveDelay:I,onPopupVisibleChange:h,forceRender:P,popupMotion:G,fresh:!0},n)}function Ar(e){var r=e.id,a=e.open,n=e.keyPath,i=e.children,o="inline",l=t.useContext(oe),u=l.prefixCls,v=l.forceSubMenuRender,c=l.motion,h=l.defaultMotions,s=l.mode,d=t.useRef(!1);d.current=s===o;var M=t.useState(!d.current),b=A(M,2),I=b[0],m=b[1],E=d.current?a:!1;t.useEffect(function(){d.current&&m(!1)},[s]);var P=k({},cn(o,c,h));n.length>1&&(P.motionAppear=!1);var p=P.onVisibleChanged;return P.onVisibleChanged=function(g){return!d.current&&!g&&m(!0),p==null?void 0:p(g)},I?null:t.createElement(De,{mode:o,locked:!d.current},t.createElement(Jn,$({visible:E},P,{forceRender:v,removeOnLeave:!1,leavedClassName:"".concat(u,"-hidden")}),function(g){var C=g.className,f=g.style;return t.createElement(It,{id:r,className:C,style:f},i)}))}var $r=["style","className","title","eventKey","warnKey","disabled","internalPopupClose","children","itemIcon","expandIcon","popupClassName","popupOffset","popupStyle","onClick","onMouseEnter","onMouseLeave","onTitleClick","onTitleMouseEnter","onTitleMouseLeave"],Dr=["active"],Lr=t.forwardRef(function(e,r){var a=e.style,n=e.className,i=e.title,o=e.eventKey;e.warnKey;var l=e.disabled,u=e.internalPopupClose,v=e.children,c=e.itemIcon,h=e.expandIcon,s=e.popupClassName,d=e.popupOffset,M=e.popupStyle,b=e.onClick,I=e.onMouseEnter,m=e.onMouseLeave,E=e.onTitleClick,P=e.onTitleMouseEnter,p=e.onTitleMouseLeave,g=re(e,$r),C=tn(o),f=t.useContext(oe),D=f.prefixCls,y=f.mode,L=f.openKeys,_=f.disabled,K=f.overflowDisabled,T=f.activeKey,F=f.selectedKeys,G=f.itemIcon,V=f.expandIcon,q=f.onItemClick,z=f.onOpenChange,B=f.onActive,H=t.useContext(Ct),Y=H._internalRenderSubMenuItem,te=t.useContext(an),ce=te.isSubPathKey,ve=Le(),U="".concat(D,"-submenu"),j=_||l,ue=t.useRef(),X=t.useRef(),O=c??G,N=h??V,ae=L.includes(o),fe=!K&&ae,Te=ce(F,o),Fe=ln(o,j,P,p),ge=Fe.active,xe=re(Fe,Dr),tt=t.useState(!1),we=A(tt,2),Ee=we[0],Ve=we[1],ze=function(J){j||Ve(J)},nt=function(J){ze(!0),I==null||I({key:o,domEvent:J})},Ue=function(J){ze(!1),m==null||m({key:o,domEvent:J})},se=t.useMemo(function(){return ge||(y!=="inline"?Ee||ce([T],o):!1)},[y,ge,T,Ee,o,ce]),rt=un(ve.length),at=function(J){j||(E==null||E({key:o,domEvent:J}),y==="inline"&&z(o,!ae))},We=Ae(function(ie){b==null||b(Ze(ie)),q(ie)}),He=function(J){y!=="inline"&&z(o,J)},it=function(){B(o)},me=C&&"".concat(C,"-popup"),Pe=t.useMemo(function(){return t.createElement(sn,{icon:y!=="horizontal"?N:void 0,props:k(k({},e),{},{isOpen:fe,isSubMenu:!0})},t.createElement("i",{className:"".concat(U,"-arrow")}))},[y,N,e,fe,U]),he=t.createElement("div",$({role:"menuitem",style:rt,className:"".concat(U,"-title"),tabIndex:j?null:-1,ref:ue,title:typeof i=="string"?i:null,"data-menu-id":K&&C?null:C,"aria-expanded":fe,"aria-haspopup":!0,"aria-controls":me,"aria-disabled":j,onClick:at,onFocus:it},xe),i,Pe),Ne=t.useRef(y);if(y!=="inline"&&ve.length>1?Ne.current="vertical":Ne.current=y,!K){var Me=Ne.current;he=t.createElement(Or,{mode:Me,prefixCls:U,visible:!u&&fe&&y!=="inline",popupClassName:s,popupOffset:d,popupStyle:M,popup:t.createElement(De,{mode:Me==="horizontal"?"vertical":Me},t.createElement(It,{id:me,ref:X},v)),disabled:j,onVisibleChange:He},he)}var ne=t.createElement($e.Item,$({ref:r,role:"none"},g,{component:"li",style:a,className:de(U,"".concat(U,"-").concat(y),n,x(x(x(x({},"".concat(U,"-open"),fe),"".concat(U,"-active"),se),"".concat(U,"-selected"),Te),"".concat(U,"-disabled"),j)),onMouseEnter:nt,onMouseLeave:Ue}),he,!K&&t.createElement(Ar,{id:me,open:fe,keyPath:ve},v));return Y&&(ne=Y(ne,e,{selected:Te,active:se,open:fe,disabled:j})),t.createElement(De,{onItemClick:We,mode:y==="horizontal"?"vertical":y,itemIcon:O,expandIcon:N},ne)}),xt=t.forwardRef(function(e,r){var a=e.eventKey,n=e.children,i=Le(a),o=St(n,i),l=Qe();t.useEffect(function(){if(l)return l.registerPath(a,i),function(){l.unregisterPath(a,i)}},[i]);var u;return l?u=o:u=t.createElement(Lr,$({ref:r},e),o),t.createElement(rn.Provider,{value:i},u)});function vn(e){var r=e.className,a=e.style,n=t.useContext(oe),i=n.prefixCls,o=Qe();return o?null:t.createElement("li",{role:"separator",className:de("".concat(i,"-item-divider"),r),style:a})}var Tr=["className","title","eventKey","children"],Fr=t.forwardRef(function(e,r){var a=e.className,n=e.title;e.eventKey;var i=e.children,o=re(e,Tr),l=t.useContext(oe),u=l.prefixCls,v="".concat(u,"-item-group");return t.createElement("li",$({ref:r,role:"presentation"},o,{onClick:function(h){return h.stopPropagation()},className:de(v,a)}),t.createElement("div",{role:"presentation",className:"".concat(v,"-title"),title:typeof n=="string"?n:void 0},n),t.createElement("ul",{role:"group",className:"".concat(v,"-list")},i))}),fn=t.forwardRef(function(e,r){var a=e.eventKey,n=e.children,i=Le(a),o=St(n,i),l=Qe();return l?o:t.createElement(Fr,$({ref:r},bt(e,["warnKey"])),o)}),Vr=["label","children","key","type","extra"];function ht(e,r,a){var n=r.item,i=r.group,o=r.submenu,l=r.divider;return(e||[]).map(function(u,v){if(u&&Zn(u)==="object"){var c=u,h=c.label,s=c.children,d=c.key,M=c.type,b=c.extra,I=re(c,Vr),m=d??"tmp-".concat(v);return s||M==="group"?M==="group"?t.createElement(i,$({key:m},I,{title:h}),ht(s,r,a)):t.createElement(o,$({key:m},I,{title:h}),ht(s,r,a)):M==="divider"?t.createElement(l,$({key:m},I)):t.createElement(n,$({key:m},I,{extra:b}),h,(!!b||b===0)&&t.createElement("span",{className:"".concat(a,"-item-extra")},b))}return null}).filter(function(u){return u})}function Bt(e,r,a,n,i){var o=e,l=k({divider:vn,item:Rt,group:fn,submenu:xt},n);return r&&(o=ht(r,l,i)),St(o,a)}var zr=["prefixCls","rootClassName","style","className","tabIndex","items","children","direction","id","mode","inlineCollapsed","disabled","disabledOverflow","subMenuOpenDelay","subMenuCloseDelay","forceSubMenuRender","defaultOpenKeys","openKeys","activeKey","defaultActiveFirst","selectable","multiple","defaultSelectedKeys","selectedKeys","onSelect","onDeselect","inlineIndent","motion","defaultMotions","triggerSubMenuAction","builtinPlacements","itemIcon","expandIcon","overflowedIndicator","overflowedIndicatorPopupClassName","getPopupContainer","onClick","onOpenChange","onKeyDown","openAnimation","openTransitionName","_internalRenderMenuItem","_internalRenderSubMenuItem","_internalComponents"],Ce=[],Ur=t.forwardRef(function(e,r){var a,n=e,i=n.prefixCls,o=i===void 0?"rc-menu":i,l=n.rootClassName,u=n.style,v=n.className,c=n.tabIndex,h=c===void 0?0:c,s=n.items,d=n.children,M=n.direction,b=n.id,I=n.mode,m=I===void 0?"vertical":I,E=n.inlineCollapsed,P=n.disabled,p=n.disabledOverflow,g=n.subMenuOpenDelay,C=g===void 0?.1:g,f=n.subMenuCloseDelay,D=f===void 0?.1:f,y=n.forceSubMenuRender,L=n.defaultOpenKeys,_=n.openKeys,K=n.activeKey,T=n.defaultActiveFirst,F=n.selectable,G=F===void 0?!0:F,V=n.multiple,q=V===void 0?!1:V,z=n.defaultSelectedKeys,B=n.selectedKeys,H=n.onSelect,Y=n.onDeselect,te=n.inlineIndent,ce=te===void 0?24:te,ve=n.motion,U=n.defaultMotions,j=n.triggerSubMenuAction,ue=j===void 0?"hover":j,X=n.builtinPlacements,O=n.itemIcon,N=n.expandIcon,ae=n.overflowedIndicator,fe=ae===void 0?"...":ae,Te=n.overflowedIndicatorPopupClassName,Fe=n.getPopupContainer,ge=n.onClick,xe=n.onOpenChange,tt=n.onKeyDown;n.openAnimation,n.openTransitionName;var we=n._internalRenderMenuItem,Ee=n._internalRenderSubMenuItem,Ve=n._internalComponents,ze=re(n,zr),nt=t.useMemo(function(){return[Bt(d,s,Ce,Ve,o),Bt(d,s,Ce,{},o)]},[d,s,Ve]),Ue=A(nt,2),se=Ue[0],rt=Ue[1],at=t.useState(!1),We=A(at,2),He=We[0],it=We[1],me=t.useRef(),Pe=yr(b),he=M==="rtl",Ne=Ye(L,{value:_,postState:function(R){return R||Ce}}),Me=A(Ne,2),ne=Me[0],ie=Me[1],J=function(R){var S=arguments.length>1&&arguments[1]!==void 0?arguments[1]:!1;function Z(){ie(R),xe==null||xe(R)}S?Qn.flushSync(Z):Z()},dn=t.useState(ne),wt=A(dn,2),mn=wt[0],pn=wt[1],ot=t.useRef(!1),gn=t.useMemo(function(){return(m==="inline"||m==="vertical")&&E?["vertical",E]:[m,!1]},[m,E]),Et=A(gn,2),je=Et[0],lt=Et[1],Pt=je==="inline",hn=t.useState(je),Nt=A(hn,2),pe=Nt[0],bn=Nt[1],Cn=t.useState(lt),Kt=A(Cn,2),yn=Kt[0],Mn=Kt[1];t.useEffect(function(){bn(je),Mn(lt),ot.current&&(Pt?ie(mn):J(Ce))},[je,lt]);var Rn=t.useState(0),kt=A(Rn,2),Be=kt[0],In=kt[1],ut=Be>=se.length-1||pe!=="horizontal"||p;t.useEffect(function(){Pt&&pn(ne)},[ne]),t.useEffect(function(){return ot.current=!0,function(){ot.current=!1}},[]);var be=br(),_t=be.registerPath,Ot=be.unregisterPath,Sn=be.refreshOverflowKeys,At=be.isSubPathKey,xn=be.getKeyPath,$t=be.getKeys,wn=be.getSubPathKeys,En=t.useMemo(function(){return{registerPath:_t,unregisterPath:Ot}},[_t,Ot]),Pn=t.useMemo(function(){return{isSubPathKey:At}},[At]);t.useEffect(function(){Sn(ut?Ce:se.slice(Be+1).map(function(w){return w.key}))},[Be,ut]);var Nn=Ye(K||T&&((a=se[0])===null||a===void 0?void 0:a.key),{value:K}),Dt=A(Nn,2),Ke=Dt[0],st=Dt[1],Kn=Ae(function(w){st(w)}),kn=Ae(function(){st(void 0)});t.useImperativeHandle(r,function(){return{list:me.current,focus:function(R){var S,Z=$t(),Q=pt(Z,Pe),qe=Q.elements,ct=Q.key2element,zn=Q.element2key,Ft=yt(me.current,qe),Vt=Ke??(Ft[0]?zn.get(Ft[0]):(S=se.find(function(Un){return!Un.props.disabled}))===null||S===void 0?void 0:S.key),ke=ct.get(Vt);if(Vt&&ke){var vt;ke==null||(vt=ke.focus)===null||vt===void 0||vt.call(ke,R)}}}});var _n=Ye(z||[],{value:B,postState:function(R){return Array.isArray(R)?R:R==null?Ce:[R]}}),Lt=A(_n,2),Ge=Lt[0],On=Lt[1],An=function(R){if(G){var S=R.key,Z=Ge.includes(S),Q;q?Z?Q=Ge.filter(function(ct){return ct!==S}):Q=[].concat(ye(Ge),[S]):Q=[S],On(Q);var qe=k(k({},R),{},{selectedKeys:Q});Z?Y==null||Y(qe):H==null||H(qe)}!q&&ne.length&&pe!=="inline"&&J(Ce)},$n=Ae(function(w){ge==null||ge(Ze(w)),An(w)}),Tt=Ae(function(w,R){var S=ne.filter(function(Q){return Q!==w});if(R)S.push(w);else if(pe!=="inline"){var Z=wn(w);S=S.filter(function(Q){return!Z.has(Q)})}Xt(ne,S,!0)||J(S,!0)}),Dn=function(R,S){var Z=S??!ne.includes(R);Tt(R,Z)},Ln=pr(pe,Ke,he,Pe,me,$t,xn,st,Dn,tt);t.useEffect(function(){it(!0)},[]);var Tn=t.useMemo(function(){return{_internalRenderMenuItem:we,_internalRenderSubMenuItem:Ee}},[we,Ee]),Fn=pe!=="horizontal"||p?se:se.map(function(w,R){return t.createElement(De,{key:w.key,overflowDisabled:R>Be},w)}),Vn=t.createElement($e,$({id:b,ref:me,prefixCls:"".concat(o,"-overflow"),component:"ul",itemComponent:Rt,className:de(o,"".concat(o,"-root"),"".concat(o,"-").concat(pe),v,x(x({},"".concat(o,"-inline-collapsed"),yn),"".concat(o,"-rtl"),he),l),dir:M,style:u,role:"menu",tabIndex:h,data:Fn,renderRawItem:function(R){return R},renderRawRest:function(R){var S=R.length,Z=S?se.slice(-S):null;return t.createElement(xt,{eventKey:gt,title:fe,disabled:ut,internalPopupClose:S===0,popupClassName:Te},Z)},maxCount:pe!=="horizontal"||p?$e.INVALIDATE:$e.RESPONSIVE,ssr:"full","data-menu-list":!0,onVisibleChange:function(R){In(R)},onKeyDown:Ln},ze));return t.createElement(Ct.Provider,{value:Tn},t.createElement(Qt.Provider,{value:Pe},t.createElement(De,{prefixCls:o,rootClassName:l,mode:pe,openKeys:ne,rtl:he,disabled:P,motion:He?ve:null,defaultMotions:He?U:null,activeKey:Ke,onActive:Kn,onInactive:kn,selectedKeys:Ge,inlineIndent:ce,subMenuOpenDelay:C,subMenuCloseDelay:D,forceSubMenuRender:y,builtinPlacements:X,triggerSubMenuAction:ue,getPopupContainer:Fe,itemIcon:O,expandIcon:N,onItemClick:$n,onOpenChange:Tt},t.createElement(an.Provider,{value:Pn},Vn),t.createElement("div",{style:{display:"none"},"aria-hidden":!0},t.createElement(nn.Provider,{value:En},rt)))))}),et=Ur;et.Item=Rt;et.SubMenu=xt;et.ItemGroup=fn;et.Divider=vn;var Wr={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M176 511a56 56 0 10112 0 56 56 0 10-112 0zm280 0a56 56 0 10112 0 56 56 0 10-112 0zm280 0a56 56 0 10112 0 56 56 0 10-112 0z"}}]},name:"ellipsis",theme:"outlined"},Hr=function(r,a){return t.createElement(er,$({},r,{ref:a,icon:Wr}))},Yr=t.forwardRef(Hr);export{qr as D,et as E,Rt as M,Yr as R,xt as S,vn as a,fn as b,Le as u};
