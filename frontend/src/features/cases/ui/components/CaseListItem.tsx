import type { FormEvent } from 'react'

import type { CaseDraft } from '../../domain/caseDraft'
import type { CaseSummary } from '../../domain/caseSummary'
import { caseStatusLabels, caseStatusOptions } from '../../domain/casePresentation'
import {
  attentionBadgeClasses,
  dangerButtonClasses,
  inputClasses,
  primaryButtonClasses,
  secondaryButtonClasses,
  statusBadgeClasses,
} from '../caseTheme'

type CaseListItemProps = {
  caseSummary: CaseSummary
  editingCaseId: number | null
  editDraft: CaseDraft
  onEditDraftChange: (draft: CaseDraft) => void
  onStartEditing: (caseSummary: CaseSummary) => void
  onCancelEditing: () => void
  onSubmitEdit: () => Promise<void>
  onDelete: (caseId: number) => Promise<void>
}

export function CaseListItem({
  caseSummary,
  editingCaseId,
  editDraft,
  onEditDraftChange,
  onStartEditing,
  onCancelEditing,
  onSubmitEdit,
  onDelete,
}: CaseListItemProps) {
  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    await onSubmitEdit()
  }

  return (
    <li className="rounded-[1.75rem] border border-stone-200 bg-stone-50/80 p-5 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
      {editingCaseId === caseSummary.id ? (
        <form className="space-y-4" onSubmit={(event) => void handleSubmit(event)}>
          <div className="grid gap-4 md:grid-cols-[1fr_220px]">
            <div className="space-y-2">
              <label className="text-sm font-semibold text-slate-700" htmlFor="edit-case-title">
                Edit title
              </label>
              <input
                className={inputClasses}
                id="edit-case-title"
                name="editTitle"
                type="text"
                value={editDraft.title}
                onChange={(event) => onEditDraftChange({ ...editDraft, title: event.target.value })}
              />
            </div>
            <div className="space-y-2">
              <label className="text-sm font-semibold text-slate-700" htmlFor="edit-case-status">
                Edit status
              </label>
              <select
                className={inputClasses}
                id="edit-case-status"
                name="editStatus"
                value={editDraft.status}
                onChange={(event) => onEditDraftChange({ ...editDraft, status: event.target.value as CaseDraft['status'] })}
              >
                {caseStatusOptions.map((statusOption) => (
                  <option key={statusOption} value={statusOption}>
                    {caseStatusLabels[statusOption]}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <div className="flex flex-wrap gap-3">
            <button className={primaryButtonClasses} type="submit">
              Save changes
            </button>
            <button className={secondaryButtonClasses} type="button" onClick={onCancelEditing}>
              Cancel
            </button>
          </div>
        </form>
      ) : (
        <div className="flex flex-col gap-4 lg:flex-row lg:items-start lg:justify-between">
          <div>
            <div className="flex flex-wrap items-center gap-2">
              <h2 className="font-display text-2xl text-slate-950">{caseSummary.title}</h2>
              <span
                className={`rounded-full px-3 py-1 text-xs font-semibold tracking-[0.18em] uppercase ${statusBadgeClasses[caseSummary.status] ?? 'bg-slate-200 text-slate-800 ring-1 ring-inset ring-slate-300'}`}
              >
                {caseSummary.status}
              </span>
            </div>
            <p className="mt-3 text-sm text-slate-500">Case #{caseSummary.id}</p>
            <div className="mt-4 flex flex-wrap gap-3">
              <span
                className={`rounded-full px-3 py-1 text-xs font-semibold tracking-[0.16em] uppercase ${attentionBadgeClasses[caseSummary.attentionLevel] ?? 'bg-stone-200 text-stone-700 ring-1 ring-inset ring-stone-300'}`}
              >
                {caseSummary.attentionLevel}
              </span>
            </div>
          </div>
          <div className="flex flex-wrap gap-3 lg:justify-end">
            <button className={secondaryButtonClasses} type="button" onClick={() => onStartEditing(caseSummary)}>
              {`Edit ${caseSummary.title}`}
            </button>
            <button className={dangerButtonClasses} type="button" onClick={() => void onDelete(caseSummary.id)}>
              {`Delete ${caseSummary.title}`}
            </button>
          </div>
        </div>
      )}
    </li>
  )
}
