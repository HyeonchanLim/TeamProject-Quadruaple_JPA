import{u as T,C as p,G as X}from"./Checkbox-CZpZuEIS.js";import{r,Y,ab as D,ac as F,ad as J,ae as h}from"./index-LV_9s5-1.js";var L=function(t,u){var o={};for(var s in t)Object.prototype.hasOwnProperty.call(t,s)&&u.indexOf(s)<0&&(o[s]=t[s]);if(t!=null&&typeof Object.getOwnPropertySymbols=="function")for(var a=0,s=Object.getOwnPropertySymbols(t);a<s.length;a++)u.indexOf(s[a])<0&&Object.prototype.propertyIsEnumerable.call(t,s[a])&&(o[s[a]]=t[s[a]]);return o};const Q=r.forwardRef((t,u)=>{const{defaultValue:o,children:s,options:a=[],prefixCls:S,className:V,rootClassName:N,style:P,onChange:f}=t,l=L(t,["defaultValue","children","options","prefixCls","className","rootClassName","style","onChange"]),{getPrefixCls:_,direction:E}=r.useContext(Y),[d,v]=r.useState(l.value||o||[]),[k,g]=r.useState([]);r.useEffect(()=>{"value"in l&&v(l.value||[])},[l.value]);const m=r.useMemo(()=>a.map(e=>typeof e=="string"||typeof e=="number"?{label:e,value:e}:e),[a]),w=e=>{g(n=>n.filter(c=>c!==e))},j=e=>{g(n=>[].concat(h(n),[e]))},I=e=>{const n=d.indexOf(e.value),c=h(d);n===-1?c.push(e.value):c.splice(n,1),"value"in l||v(c),f==null||f(c.filter(x=>k.includes(x)).sort((x,H)=>{const K=m.findIndex(b=>b.value===x),M=m.findIndex(b=>b.value===H);return K-M}))},i=_("checkbox",S),C=`${i}-group`,y=D(i),[G,$,A]=T(i,y),q=F(l,["value","disabled"]),B=a.length?m.map(e=>r.createElement(p,{prefixCls:i,key:e.value.toString(),disabled:"disabled"in e?e.disabled:l.disabled,value:e.value,checked:d.includes(e.value),onChange:e.onChange,className:`${C}-item`,style:e.style,title:e.title,id:e.id,required:e.required},e.label)):s,R={toggleOption:I,value:d,disabled:l.disabled,name:l.name,registerValue:j,cancelValue:w},z=J(C,{[`${C}-rtl`]:E==="rtl"},V,N,A,y,$);return G(r.createElement("div",Object.assign({className:z,style:P},q,{ref:u}),r.createElement(X.Provider,{value:R},B)))}),O=p;O.Group=Q;O.__ANT_CHECKBOX=!0;export{O as C};
